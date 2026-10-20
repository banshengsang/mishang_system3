package com.mishang.mishang_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 订单表
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
public class Orders implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "oid", type = IdType.AUTO)
    private Integer oid;

    /**
     * 外键客户id
     */
    private Integer customerId;

    /**
     * 下单时间
     */
    private LocalDateTime orderTime;

    /**
     * 总金额
     */
    private BigDecimal totalAmount;

    /**
     * 支付方式
     */
    private String paymenrType;

    /**
     * 是否结清
     */
    private Boolean isSettled;

    public Integer getOid() {
        return oid;
    }

    public void setOid(Integer oid) {
        this.oid = oid;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public LocalDateTime getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(LocalDateTime orderTime) {
        this.orderTime = orderTime;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public String getPaymenrType() {
        return paymenrType;
    }

    public void setPaymenrType(String paymenrType) {
        this.paymenrType = paymenrType;
    }

    public Boolean getIsSettled() {
        return isSettled;
    }

    public void setIsSettled(Boolean isSettled) {
        this.isSettled = isSettled;
    }

    @Override
    public String toString() {
        return "Orders{" +
        "oid = " + oid +
        ", customerId = " + customerId +
        ", orderTime = " + orderTime +
        ", totalAmount = " + totalAmount +
        ", paymenrType = " + paymenrType +
        ", isSettled = " + isSettled +
        "}";
    }
}
