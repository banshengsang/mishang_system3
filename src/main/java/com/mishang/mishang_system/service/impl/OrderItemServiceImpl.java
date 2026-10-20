package com.mishang.mishang_system.service.impl;

import com.mishang.mishang_system.entity.OrderItem;
import com.mishang.mishang_system.mapper.OrderItemMapper;
import com.mishang.mishang_system.service.IOrderItemService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 订单明细表 服务实现类
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@Service
public class OrderItemServiceImpl extends ServiceImpl<OrderItemMapper, OrderItem> implements IOrderItemService {

}
