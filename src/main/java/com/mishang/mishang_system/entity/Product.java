package com.mishang.mishang_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 商品表
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
public class Product implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "product_id", type = IdType.AUTO)
    private Integer productId;

    /**
     * 茶名
     */
    private String productName;

    /**
     * 茶种类
     */
    private String productCategory;

    /**
     * 等级
     */
    private Integer productGrade;

    /**
     * 年份
     */
    private LocalDateTime productYear;

    /**
     * 规格
     */
    private String spec;

    /**
     * 零售价
     */
    private BigDecimal price;

    /**
     * 当前库存数量
     */
    private Integer stockQuantity;

    /**
     * 库存警戒线
     */
    //每日消耗量
    private BigDecimal dailyConsume;

    //getter、setter
    public BigDecimal getDailyConsume() {
        return dailyConsume;
    }
    public void setDailyConsume(BigDecimal dailyConsume) {
        this.dailyConsume = dailyConsume;
    }

    private Integer stockDeadline;

    public Integer getProductId() {
        return productId;
    }

    public void setProductId(Integer productId) {
        this.productId = productId;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductCategory() {
        return productCategory;
    }

    public void setProductCategory(String productCategory) {
        this.productCategory = productCategory;
    }

    public Integer getProductGrade() {
        return productGrade;
    }

    public void setProductGrade(Integer productGrade) {
        this.productGrade = productGrade;
    }

    public LocalDateTime getProductYear() {
        return productYear;
    }

    public void setProductYear(LocalDateTime productYear) {
        this.productYear = productYear;
    }

    public String getSpec() {
        return spec;
    }

    public void setSpec(String spec) {
        this.spec = spec;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Integer getStockDeadline() {
        return stockDeadline;
    }

    public void setStockDeadline(Integer stockDeadline) {
        this.stockDeadline = stockDeadline;
    }

    @Override
    public String toString() {
        return "Product{" +
        "productId = " + productId +
        ", productName = " + productName +
        ", productCategory = " + productCategory +
        ", productGrade = " + productGrade +
        ", productYear = " + productYear +
        ", spec = " + spec +
        ", price = " + price +
        ", stockQuantity = " + stockQuantity +
        ", stockDeadline = " + stockDeadline +
        "}";
    }
}
