package com.mishang.mishang_system.mapper;

import com.mishang.mishang_system.entity.Orders;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 订单表 Mapper 接口
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
public interface OrdersMapper extends BaseMapper<Orders> {
    List<Orders> getLatestOrdersByCustomerIds(@Param("customerIds") List<Integer> customerIds);

}
