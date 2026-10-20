package com.mishang.mishang_system.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.mishang.mishang_system.entity.*;
import com.mishang.mishang_system.mapper.CustomerMapper;
import com.mishang.mishang_system.service.ICustomerService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.mishang.mishang_system.service.IOrderItemService;
import com.mishang.mishang_system.service.IOrdersService;
import com.mishang.mishang_system.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 客户表 服务实现类
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@Service
public class CustomerServiceImpl extends ServiceImpl<CustomerMapper, Customer> implements ICustomerService {
    @Autowired
    private IOrdersService ordersService;
    @Autowired
    private IOrderItemService orderItemService;
    @Autowired
    private IProductService productService;
    @Override
    //返回剩余天数
    public List<CustomerVO> getRestockReminders(){
        //===========================1、查询客户列表==========================
        //查询全部客户
        List<Customer> customerList=this.list();

        //如果客户为空，直接返回空集合
        if (CollUtil.isEmpty(customerList)){//记得添加Maven依赖
            return Collections.emptyList();
        }

        //提取所有客户的id成一个list
        List<Integer> customerIds = customerList.stream()//相比较Integer，Long能存的数据更多
                //遍历客户并每次都调用getId这个方法（即取出客户id）（：：这个是方法引用）
                // ，map是用来映射的（即改变类型）（把原来customer类型改成整型（id））
                .map(Customer::getId)
                //收集成list集合
                .collect(Collectors.toList());

        //==========================2、一次性查出每个客户的最新一笔订单===========
        //这里调用自定义的mapper方法,取出订单
        List<Orders> latestOrders =ordersService.getLatestOrdersByCustomerIds(customerIds);

        // 把订单list转成Map：key=客户ID，value=该客户最新订单
        Map<Integer,Orders> orderMap = latestOrders.stream()
                .collect(Collectors.toMap(//collect是收集器把数据收集进map里面。Collectors是工具后面调用toMap是打包成map
                        Orders::getCustomerId,//参数一。每个订单对象调用getCustomerId方法返回客户id作为键
                        o->o,          //参数二。等价于orders—>orders，o意思是流里面遍历到的单个订单对象（作为值）
                        (o1,o2)->o1//如果同一个key取出了两个订单o1和o2那么保留第一个订单
                ));

        //提取所有最新订单的id
        List<Integer> orderIds = latestOrders.stream()
                .map(Orders::getOid)
                .collect(Collectors.toList());
        //如果没有订单直接返回空
        if(CollUtil.isEmpty(orderIds)){
            return Collections.emptyList();
        }

        //===========================3、批量查询所有需要订单的明细==========================
        //因为订单表只存了下单时间客户和金额还用连接查询买的商品
        // 根据订单id集合in批量查询所有订单项，一次性查出，不是循环查
        List<OrderItem> orderItemList = orderItemService.lambdaQuery()
                .in(OrderItem::getOiid,orderIds)//in就是sql语句里面的IN
                .list();

        //分组：按orderId(用groupingBy）分组，同一个订单的所有订单项放到一个list
        Map<Integer,List<OrderItem>> itemGroupByOrderId = orderItemList.stream()
                .collect(Collectors.groupingBy(OrderItem::getOrderId));

        // 转换成Map：key=订单ID，value=该订单第一条订单项
        Map<Integer,OrderItem> itemMap= itemGroupByOrderId.entrySet().stream()
                .collect(Collectors.toMap(
                        Map.Entry::getKey,                  // key：订单id
                        entry -> entry.getValue().get(0)    // value：取该订单下第一条明细
                        //entry是键值对的组合
                ));

        // ===================== 第四步：批量查询商品【SQL 4次】=====================
        //提取所有订单项里面的商品id
        List<Integer> productIds = orderItemList.stream()
                .map(OrderItem::getIdProduct)//取出商品id
                .filter(Objects::nonNull)//去掉商品id为null的商品
                .distinct()//去重
                .collect(Collectors.toList());
        //批量根据商品id查询商品信息
        Map<Integer, Product> productMap = productService.listByIds(productIds).stream()
                .collect(Collectors.toMap(
                        Product::getProductId,
                        p->p
                ));

        // 把当前日期提到循环外部，避免循环重复创建对象，节省性能
        LocalDate today = LocalDate.now();
        // 创建集合存放最终组装好的VO对象
        List<CustomerVO> resultList = new ArrayList<>();


        // ===================== 第五步：循环客户【只操作内存！！无数据库访问】=====================
        //增强for循环，遍历List（把list里面每一个Customer对象，挨个拿出来，赋值给变量customer，循环执行大括号里面代码）
        for(Customer customer :customerList){
            //创建VO对象，用于返回前端
            CustomerVO vo= new CustomerVO();
            // 客户基础信息赋值
            vo.setId(customer.getId());
            vo.setCustomerName(customer.getCustomerName());
            vo.setPhone(customer.getCustomerPhoneNmber());
            vo.setTags(customer.getTags());
            // 从内存Map拿这个客户的最新订单，不查数据库
            Orders lastOrder =orderMap.get(customer.getId());
            //判空
            if (lastOrder == null) {
                // 该客户无订单，跳过
                continue;
            }
            // 根据订单id，从内存Map拿第一条订单项
            OrderItem item = itemMap.get(lastOrder.getOid());
            if (item == null) {
                // 该订单没有明细，跳过
                continue;
            }
            // 根据商品id，内存Map拿商品信息
            Product product = productMap.get(item.getIdProduct());
            if(product == null){
                continue;
            }
            // ===================== 业务计算：剩余可用天数 =====================
            int totalDays;
            //购买数量
            //BigDecimal是引用数据类型，用来精确处理浮点数的小数运算
            BigDecimal buyNum = new BigDecimal(item.getOquantity());
            //商品每日消耗量
            BigDecimal dailyConsume = product.getDailyConsume();
            //compareTo是BigDecimal用来比大小的方法返回大于0就是正常消耗
            if(dailyConsume == null || dailyConsume.compareTo(BigDecimal.ZERO) <=0){
                totalDays = 0;
            }else {
                // 总可用天数 = 购买数量 / 每日消耗量，向上取整
                //divide（除数，保留小数点后几位，参数三）RoundingMode.CEILING向上取整
                BigDecimal totalDayDecimal = buyNum.divide(dailyConsume, 2, RoundingMode.CEILING);
                //intValue()把BigDecimal数字转成int整数
                totalDays = totalDayDecimal.intValue();
            }
            if(lastOrder.getOrderTime() == null){
                continue;
            }
            //计算到今天中间经过了多少天
            int passedDay = Math.toIntExact(ChronoUnit.DAYS.between(lastOrder.getOrderTime().toLocalDate(), today));
            //剩余天数=总天数-已经过去的天数
            int remain =Math.max(totalDays-passedDay,0);
            //赋值
            vo.setRemainingDays(remain);
            //将组装好的vo添加进集合
            resultList.add(vo);
            }
        //===================== 第六步：内存过滤、排序、返回补货提醒=====================
        return resultList.stream()
                //过滤条件：剩余天数不为空，且小于2即需要补货
                .filter(vo->vo.getRemainingDays()!=null&&vo.getRemainingDays()<=2)
                //按剩余天数升序排序
                //.sorted()Stream 流的方法：对集合里面的元素进行排序，放在 stream 后面用
                // Comparator.comparingInt( 方法引用 )comparingInt：专门用来按 int 类型字段排序的比较器
                .sorted(Comparator.comparingInt(CustomerVO::getRemainingDays))
                .collect(Collectors.toList());
        }

}
