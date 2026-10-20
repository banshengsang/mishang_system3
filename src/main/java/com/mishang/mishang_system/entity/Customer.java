package com.mishang.mishang_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * <p>
 * 客户表
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 客户ID
     */
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;

    /**
     * 姓名
     */
    private String customerName;

    /**
     * 电话
     */
    private String customerPhoneNmber;

    /**
     * 生日
     */
    private LocalDate customerBirthday;

    /**
     * 折扣
     */
    private BigDecimal discountRate;

    /**
     * 储值
     */
    private BigDecimal balance;

    /**
     * 标签
     */
    private String tags;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getCustomerPhoneNmber() {
        return customerPhoneNmber;
    }

    public void setCustomerPhoneNmber(String customerPhoneNmber) {
        this.customerPhoneNmber = customerPhoneNmber;
    }

    public LocalDate getCustomerBirthday() {
        return customerBirthday;
    }

    public void setCustomerBirthday(LocalDate customerBirthday) {
        this.customerBirthday = customerBirthday;
    }

    public BigDecimal getDiscountRate() {
        return discountRate;
    }

    public void setDiscountRate(BigDecimal discountRate) {
        this.discountRate = discountRate;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Customer{" +
        "id = " + id +
        ", customerName = " + customerName +
        ", customerPhoneNmber = " + customerPhoneNmber +
        ", customerBirthday = " + customerBirthday +
        ", discountRate = " + discountRate +
        ", balance = " + balance +
        ", tags = " + tags +
        ", createTime = " + createTime +
        "}";
    }
}
