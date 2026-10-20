package com.mishang.mishang_system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mishang.mishang_system.entity.AccountLog;
import com.mishang.mishang_system.entity.CreateOrderRequest;
import com.mishang.mishang_system.entity.Customer;
import com.mishang.mishang_system.entity.CustomerPreference;
import com.mishang.mishang_system.entity.InventoryBatch;
import com.mishang.mishang_system.entity.OrderItem;
import com.mishang.mishang_system.entity.Orders;
import com.mishang.mishang_system.entity.Product;
import com.mishang.mishang_system.exception.BusinessException;
import com.mishang.mishang_system.mapper.CustomerMapper;
import com.mishang.mishang_system.mapper.OrdersMapper;
import com.mishang.mishang_system.service.IAccountLogService;
import com.mishang.mishang_system.service.ICustomerPreferenceService;
import com.mishang.mishang_system.service.ICustomerService;
import com.mishang.mishang_system.service.IInventoryBatchService;
import com.mishang.mishang_system.service.IOrderItemService;
import com.mishang.mishang_system.service.IOrdersService;
import com.mishang.mishang_system.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 订单表 服务实现类
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@Service
public class OrdersServiceImpl extends ServiceImpl<OrdersMapper, Orders> implements IOrdersService {

    private static final String PAY_CASH = "现金";
    private static final String PAY_TRANSFER = "转账";
    private static final String PAY_CREDIT = "赊账";

    @Autowired
    private OrdersMapper ordersMapper;
    @Autowired
    private CustomerMapper customerMapper;
    @Autowired
    private IProductService productService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IInventoryBatchService inventoryBatchService;
    @Autowired
    private IAccountLogService accountLogService;
    @Autowired
    private ICustomerPreferenceService customerPreferenceService;

    @Override
    public List<Orders> getLatestOrdersByCustomerIds(List<Integer> customerIds) {
        return ordersMapper.getLatestOrdersByCustomerIds(customerIds);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Orders placeOrder(CreateOrderRequest request) {
        validateRequest(request);

        Customer customer = customerMapper.selectById(request.getCustomerId());
        if (customer == null) {
            throw new BusinessException("客户不存在");
        }

        String paymentType = request.getPaymentType().trim();
        if (!PAY_CASH.equals(paymentType) && !PAY_TRANSFER.equals(paymentType) && !PAY_CREDIT.equals(paymentType)) {
            throw new BusinessException("支付方式只能是：现金、转账、赊账");
        }

        savePreferences(request, customer.getId());

        List<OrderItem> orderItems = new ArrayList<>();
        BigDecimal totalAmount = BigDecimal.ZERO;
        BigDecimal discount = resolveDiscountRate(customer);

        for (CreateOrderRequest.OrderLineRequest line : request.getItems()) {
            Product product = productService.getById(line.getProductId());
            if (product == null) {
                throw new BusinessException("商品不存在，id=" + line.getProductId());
            }
            if (line.getQuantity() == null || line.getQuantity() <= 0) {
                throw new BusinessException("商品数量必须大于 0");
            }
            if (product.getStockQuantity() == null || product.getStockQuantity() < line.getQuantity()) {
                throw new BusinessException("商品「" + product.getProductName() + "」库存不足");
            }

            BigDecimal unitPrice = line.getUnitPrice();
            if (unitPrice == null) {
                if (product.getPrice() == null) {
                    throw new BusinessException("商品「" + product.getProductName() + "」未设置零售价");
                }
                unitPrice = product.getPrice().multiply(discount).setScale(2, RoundingMode.HALF_UP);
            }

            BigDecimal lineAmount = unitPrice.multiply(BigDecimal.valueOf(line.getQuantity()));
            totalAmount = totalAmount.add(lineAmount);

            OrderItem item = new OrderItem();
            item.setIdProduct(line.getProductId());
            item.setOquantity(line.getQuantity());
            item.setUnitPrice(unitPrice);
            orderItems.add(item);

            deductStock(product, line.getQuantity());
        }

        LocalDateTime now = LocalDateTime.now();
        Orders order = new Orders();
        order.setCustomerId(customer.getId());
        order.setOrderTime(now);
        order.setTotalAmount(totalAmount);
        order.setPaymenrType(paymentType);
        order.setIsSettled(!PAY_CREDIT.equals(paymentType));
        save(order);

        for (OrderItem item : orderItems) {
            item.setOrderId(order.getOid());
            orderItemService.save(item);
        }

        handlePayment(customer, paymentType, totalAmount, now);

        return order;
    }

    private void validateRequest(CreateOrderRequest request) {
        if (request == null) {
            throw new BusinessException("请求不能为空");
        }
        if (request.getCustomerId() == null) {
            throw new BusinessException("请选择客户");
        }
        if (!StringUtils.hasText(request.getPaymentType())) {
            throw new BusinessException("请选择支付方式");
        }
        if (CollUtil.isEmpty(request.getItems())) {
            throw new BusinessException("订单明细不能为空");
        }
    }

    private BigDecimal resolveDiscountRate(Customer customer) {
        BigDecimal rate = customer.getDiscountRate();
        if (rate == null || rate.compareTo(BigDecimal.ZERO) <= 0) {
            return BigDecimal.ONE;//折扣比0小就返回1
        }
        if (rate.compareTo(BigDecimal.ONE) > 0) {//折扣比1大应该是百分比
            return rate.divide(BigDecimal.valueOf(100), 4, RoundingMode.HALF_UP);//折扣除以100就是0点几可以直接当系数
        }
        return rate;//折扣在0到1中间直接当系数
    }

    private void savePreferences(CreateOrderRequest request, Integer customerId) {
        if (CollUtil.isEmpty(request.getPreferences())) {
            return;
        }
        for (CreateOrderRequest.PreferenceLineRequest pref : request.getPreferences()) {
            if (!StringUtils.hasText(pref.getPreferenceKey())) {
                continue;
            }
            CustomerPreference row = new CustomerPreference();
            row.setCustomerId(customerId);
            row.setPreferenceKey(pref.getPreferenceKey().trim());
            row.setPreferenceValue(Objects.toString(pref.getPreferenceValue(), ""));
            row.setBuyScene(pref.getBuyScene());
            customerPreferenceService.save(row);
        }
    }

    private void deductStock(Product product, int quantity) {
        product.setStockQuantity(product.getStockQuantity() - quantity);
        productService.updateById(product);

        List<InventoryBatch> batches = inventoryBatchService.lambdaQuery()
                .eq(InventoryBatch::getIdProduct, product.getProductId())
                .gt(InventoryBatch::getRemainingQuantity, 0)
                .orderByAsc(InventoryBatch::getPurchasseDate)
                .list();

        int remaining = quantity;
        for (InventoryBatch batch : batches) {
            if (remaining <= 0) {
                break;
            }
            int deduct = Math.min(batch.getRemainingQuantity(), remaining);
            batch.setRemainingQuantity(batch.getRemainingQuantity() - deduct);
            inventoryBatchService.updateById(batch);
            remaining -= deduct;
        }
        if (remaining > 0) {
            throw new BusinessException("商品「" + product.getProductName() + "」批次库存不足，请先补录进货批次");
        }
    }

    /**
     * 流水：amount 为消费金额（正数），typess 为现金/转账/赊账。
     * balance 仅累计赊账欠款，现金与转账不改动 balance。
     */
    private void handlePayment(Customer customer, String paymentType, BigDecimal totalAmount, LocalDateTime now) {
        if (PAY_CREDIT.equals(paymentType)) {
            BigDecimal balance = customer.getBalance() == null ? BigDecimal.ZERO : customer.getBalance();
            customer.setBalance(balance.add(totalAmount));
            customerMapper.updateById(customer);
        }
        writeAccountLog(customer.getId(), totalAmount, paymentType, now);
    }

    private void writeAccountLog(Integer customerId, BigDecimal amount, String typess, LocalDateTime logTime) {
        AccountLog log = new AccountLog();
        log.setCustomerId(customerId);
        log.setAmount(amount);
        log.setTypess(typess);
        log.setLogTime(logTime);
        accountLogService.save(log);
    }
}
