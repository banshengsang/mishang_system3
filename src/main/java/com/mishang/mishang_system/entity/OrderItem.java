package com.mishang.mishang_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * <p>
 * 订单明细表
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@TableName("order_item")
public class OrderItem implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "oiid", type = IdType.AUTO)
    private Integer oiid;

    /**
     * 外键关联订单
     */
    private Integer orderId;

    /**
     * 外键关联商品
     */
    private Integer idProduct;

    /**
     * 数量
     */
    private Integer oquantity;

    /**
     * 实际交易金额
     */
    private BigDecimal unitPrice;

    public Integer getOiid() {
        return oiid;
    }

    public void setOiid(Integer oiid) {
        this.oiid = oiid;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getIdProduct() {
        return idProduct;
    }

    public void setIdProduct(Integer idProduct) {
        this.idProduct = idProduct;
    }

    public Integer getOquantity() {
        return oquantity;
    }

    public void setOquantity(Integer oquantity) {
        this.oquantity = oquantity;
    }

    public BigDecimal getUnitPrice() {
        return unitPrice;
    }

    public void setUnitPrice(BigDecimal unitPrice) {
        this.unitPrice = unitPrice;
    }

    @Override
    public String toString() {
        return "OrderItem{" +
        "oiid = " + oiid +
        ", orderId = " + orderId +
        ", idProduct = " + idProduct +
        ", oquantity = " + oquantity +
        ", unitPrice = " + unitPrice +
        "}";
    }
}
