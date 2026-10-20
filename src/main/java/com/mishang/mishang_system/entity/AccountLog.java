package com.mishang.mishang_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * <p>
 * 账户流水表
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@TableName("account_log")
public class AccountLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "aid", type = IdType.AUTO)
    private Integer aid;

    /**
     * 外键关联客人
     */
    private Integer customerId;

    /**
     * 变动金额
     */
    private BigDecimal amount;

    /**
     * 变动类型
     */
    private String typess;

    /**
     * 时间
     */
    private LocalDateTime logTime;

    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getTypess() {
        return typess;
    }

    public void setTypess(String typess) {
        this.typess = typess;
    }

    public LocalDateTime getLogTime() {
        return logTime;
    }

    public void setLogTime(LocalDateTime logTime) {
        this.logTime = logTime;
    }

    @Override
    public String toString() {
        return "AccountLog{" +
        "aid = " + aid +
        ", customerId = " + customerId +
        ", amount = " + amount +
        ", typess = " + typess +
        ", logTime = " + logTime +
        "}";
    }
}
