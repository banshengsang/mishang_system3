package com.mishang.mishang_system.controller;

import com.mishang.mishang_system.entity.Customer;
import com.mishang.mishang_system.entity.InventoryBatch;
import com.mishang.mishang_system.service.IInventoryBatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 库存批次表 前端控制器
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@Controller
@RequestMapping("/inventoryBatch")
public class InventoryBatchController {
    @Autowired
    private IInventoryBatchService iInventoryBatchService;
    // 根据商品ID查批次列表
    @GetMapping("/list/{productId}")
    public List<InventoryBatch> listByProductId(@PathVariable Integer productId) {
        return iInventoryBatchService.lambdaQuery()
                .eq(InventoryBatch::getIdProduct, productId)
                .list();
    }
    // 1. 新增批次
    @PostMapping("/add")
    public String add(@RequestBody InventoryBatch inventoryBatch) { // 参数改成实体类
        iInventoryBatchService.save(inventoryBatch);
        return "新增成功";
    }

    // 2. 更新批次
    @PutMapping("/update")
    public String update(@RequestBody InventoryBatch inventoryBatch) { // 参数改成实体类
        iInventoryBatchService.updateById(inventoryBatch);
        return "更新成功";
    }

    // 3. 删除批次（注意路径的写法也要改！）
    @DeleteMapping("/delete/{id}") // 路径用花括号 {id}
    public String delete(@PathVariable Integer id) { // 用 @PathVariable 从路径拿ID
        iInventoryBatchService.removeById(id);
        return "删除成功";
    }
}
