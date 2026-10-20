package com.mishang.mishang_system.controller;

import com.mishang.mishang_system.entity.CreateOrderRequest;
import com.mishang.mishang_system.entity.Orders;
import com.mishang.mishang_system.exception.BusinessException;
import com.mishang.mishang_system.service.IOrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 订单表 前端控制器
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@RestController
@RequestMapping("/orders")
public class OrdersController {

    @Autowired
    private IOrdersService iordersService;

    @GetMapping("/list")
    public List<Orders> list(){
        return iordersService.list();
    }

    /**
     * 订单闭环：一次提交订单、偏好、扣库存、记流水
     */
    @PostMapping("/place")
    public ResponseEntity<Map<String, Object>> placeOrder(@RequestBody CreateOrderRequest request) {
        try {
            Orders order = iordersService.placeOrder(request);
            Map<String, Object> body = new HashMap<>();
            body.put("success", true);
            body.put("message", "下单成功");
            body.put("order", order);
            return ResponseEntity.ok(body);
        } catch (BusinessException e) {
            Map<String, Object> body = new HashMap<>();
            body.put("success", false);
            body.put("message", e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
        }
    }

    @PostMapping("/add")
    public String add (@RequestBody Orders orders){
        iordersService.save(orders);
        return "新增成功";
    }
    @PutMapping("/update")
    public String update(@RequestBody Orders orders){
        iordersService.updateById(orders);
        return "更新成功";
    }
    @DeleteMapping("/delete(id)")
    public String delete(@RequestBody Orders orders){
        iordersService.removeById(orders);
        return "删除成功";
    }
    @PostMapping("/create")
    public String createOrder (@RequestBody CreateOrderRequest request){
        iordersService.placeOrder(request);
        return "添加成功";
    }
}
