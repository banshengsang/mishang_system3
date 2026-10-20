package com.mishang.mishang_system.service.impl;

import com.mishang.mishang_system.entity.Product;
import com.mishang.mishang_system.mapper.ProductMapper;
import com.mishang.mishang_system.service.IProductService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 商品表 服务实现类
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@Service
public class ProductServiceImpl extends ServiceImpl<ProductMapper, Product> implements IProductService {

}
