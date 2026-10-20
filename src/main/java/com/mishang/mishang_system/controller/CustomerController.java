package com.mishang.mishang_system.controller;

import com.mishang.mishang_system.entity.Customer;
import com.mishang.mishang_system.entity.CustomerVO;
import com.mishang.mishang_system.service.ICustomerService;
import org.springframework.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 客户表 前端控制器
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@RestController
@RequestMapping("/customer")
public class CustomerController {
    @Autowired
    private ICustomerService icustomerService;
    @GetMapping("/list")
    public List<Customer> list(){
        return icustomerService.list();
    }

    @PostMapping("/add")
    public String add (@RequestBody Customer customer){
        icustomerService.save(customer);
        return "新增成功";
    }
    @PutMapping("/update")
    public String update(@RequestBody Customer customer){
        icustomerService.updateById(customer);
        return "更新成功";
    }
    @DeleteMapping("/delete/{id}")
    public String delete(@PathVariable Integer id){
        icustomerService.removeById(id);
        return "删除成功";
    }
    @GetMapping("/search")
    public  List<Customer> search(@RequestParam (required = false)String name){
        List<Customer> list = icustomerService.lambdaQuery()
                .like(StringUtils.hasText(name), Customer::getCustomerName, name)
                .list();
        return list;
    }
    //这就是Apifox要访问的接口路径！
    @GetMapping("/restock")
    public List<CustomerVO> restockRemind() {
        //调用你ServiceImpl里面写好的补货提醒方法
        return icustomerService.getRestockReminders();
    }
}
