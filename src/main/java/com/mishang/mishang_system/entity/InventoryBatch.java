package com.mishang.mishang_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 库存批次表
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@TableName("inventory_batch")
public class InventoryBatch implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "inventory_id", type = IdType.AUTO)
    private Integer inventoryId;

    /**
     * 商品id
     */
    private Integer idProduct;

    /**
     * 批次号
     */
    private Integer batchNo;

    /**
     * 底价
     */
    private BigDecimal purchasePrice;

    /**
     * 进货量
     */
    private Integer quantity;

    /**
     * 剩余量
     */
    private Integer remainingQuantity;

    /**
     * 进货时间
     */
    private LocalDateTime purchasseDate;

    public Integer getInventoryId() {
        return inventoryId;
    }

    public void setInventoryId(Integer inventoryId) {
        this.inventoryId = inventoryId;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getBatchNo() {
        return batchNo;
    }

    public void setBatchNo(Integer batchNo) {
        this.batchNo = batchNo;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Integer getRemainingQuantity() {
        return remainingQuantity;
    }

    public void setRemainingQuantity(Integer remainingQuantity) {
        this.remainingQuantity = remainingQuantity;
    }

    public LocalDateTime getPurchasseDate() {
        return purchasseDate;
    }

    public void setPurchasseDate(LocalDateTime purchasseDate) {
        this.purchasseDate = purchasseDate;
    }

    @Override
    public String toString() {
        return "InventoryBatch{" +
        "inventoryId = " + inventoryId +
        ", idProduct = " + idProduct +
        ", batchNo = " + batchNo +
        ", purchasePrice = " + purchasePrice +
        ", quantity = " + quantity +
        ", remainingQuantity = " + remainingQuantity +
        ", purchasseDate = " + purchasseDate +
        "}";
    }
}
