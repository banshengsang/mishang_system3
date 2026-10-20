package com.mishang.mishang_system.controller;

import com.mishang.mishang_system.entity.OrderItem;
import com.mishang.mishang_system.service.IOrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 订单明细表 前端控制器
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@Controller
@RequestMapping("/orderItem")
public class OrderItemController {

    @Autowired
    private IOrderItemService iorderItemService;
    @GetMapping("/list")
    public List<OrderItem> list(){
        return iorderItemService.list();
    }

    @PostMapping("/add")
    public String add (@RequestBody OrderItem orderItem){
        iorderItemService.save(orderItem);
        return "新增成功";
    }
    @PutMapping("/update")
    public String update(@RequestBody OrderItem orderItem){
        iorderItemService.updateById(orderItem);
        return "更新成功";
    }
    @DeleteMapping("/delete(id)")
    public String delete(@RequestBody OrderItem orderItem){
        iorderItemService.removeById(orderItem);
        return "删除成功";
    }

}
