package com.mishang.mishang_system.controller;

import com.mishang.mishang_system.entity.Customer;
import com.mishang.mishang_system.entity.CustomerPreference;
import com.mishang.mishang_system.service.ICustomerPreferenceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 客户偏好表 前端控制器
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@Controller
@RequestMapping("/customerPreference")
public class CustomerPreferenceController {

    @Autowired
    private ICustomerPreferenceService icustomerPreferenceService;
    @GetMapping("/list")
    public List<CustomerPreference> list(){
        return icustomerPreferenceService.list();
    }

    @PostMapping("/add")
    public String add (@RequestBody CustomerPreference customerPreference){
        icustomerPreferenceService.save(customerPreference);
        return "新增成功";
    }
    @PutMapping("/update")
    public String update(@RequestBody CustomerPreference customerPreference){
        icustomerPreferenceService.updateById(customerPreference);
        return "更新成功";
    }
    @DeleteMapping("/delete(id)")
    public String delete(@RequestBody CustomerPreference customerPreference){
        icustomerPreferenceService.removeById(customerPreference);
        return "删除成功";
    }

}
