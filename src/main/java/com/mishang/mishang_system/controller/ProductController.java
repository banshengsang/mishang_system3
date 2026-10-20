package com.mishang.mishang_system.controller;

import com.mishang.mishang_system.entity.Product;
import com.mishang.mishang_system.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.stereotype.Controller;

import java.util.List;

/**
 * <p>
 * 商品表 前端控制器
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@Controller
@RequestMapping("/product")
public class ProductController {
    @Autowired
    private IProductService iproductService;
    @GetMapping("/list")
    public List<Product> list(){
        return iproductService.list();
    }

    @PostMapping("/add")
    public String add (@RequestBody Product product){
        iproductService.save(product);
        return "新增成功";
    }
    @PutMapping("/update")
    public String update(@RequestBody Product product){
        iproductService.updateById(product);
        return "更新成功";
    }
    @DeleteMapping("/delete(id)")
    public String delete(@RequestBody Product product){
        iproductService.removeById(product);
        return "删除成功";
    }
    @GetMapping("/search")
    public  List<Product> search(@RequestParam (required = false)String keyword){
        List<Product> list = iproductService.lambdaQuery()
                // 如果关键字有值，就同时模糊匹配茶名或类别
                .like(keyword != null && !keyword.trim().isEmpty(), Product::getProductName, keyword)
                .or()
                .like(keyword != null && !keyword.trim().isEmpty(), Product::getProductCategory, keyword)
                .list();
        return list;
    }
}
