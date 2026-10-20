package com.mishang.mishang_system.service;

import com.mishang.mishang_system.entity.CreateOrderRequest;
import com.mishang.mishang_system.entity.Orders;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 订单表 服务类
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
public interface IOrdersService extends IService<Orders> {
    /**
     * 根据客户id集合，一次性获取每个客户最新订单
     * @param customerIds 客户ID集合，Integer类型
     * @return 每个客户对应的最新订单列表
     */
    List<Orders> getLatestOrdersByCustomerIds(List<Integer> customerIds);

    /**
     * 订单闭环：写订单与明细、偏好，扣库存，记消费流水；赊账时累加客户 balance
     */
    Orders placeOrder(CreateOrderRequest request);

}
