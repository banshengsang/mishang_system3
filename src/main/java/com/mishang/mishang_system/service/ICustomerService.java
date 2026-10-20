package com.mishang.mishang_system.service;

import com.mishang.mishang_system.entity.Customer;
import com.baomidou.mybatisplus.extension.service.IService;
import com.mishang.mishang_system.entity.CustomerVO;

import java.util.List;

/**
 * <p>
 * 客户表 服务类
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
public interface ICustomerService extends IService<Customer> {
    List<CustomerVO> getRestockReminders();//查找返回

}
