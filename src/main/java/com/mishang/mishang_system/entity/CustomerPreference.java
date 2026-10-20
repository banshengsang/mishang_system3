package com.mishang.mishang_system.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

/**
 * <p>
 * 客户偏好表
 * </p>
 *
 * @author banshengsang
 * @since 2026-09-20
 */
@TableName("customer_preference")
public class CustomerPreference implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
    @TableId(value = "pid", type = IdType.AUTO)
    private Integer pid;

    /**
     * 客户id
     */
    private Integer customerId;

    /**
     * 偏好项
     */
    private String preferenceKey;

    /**
     * 偏好值
     */
    private String preferenceValue;

    /**
     * 购买场景
     */
    private String buyScene;

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getPreferenceKey() {
        return preferenceKey;
    }

    public void setPreferenceKey(String preferenceKey) {
        this.preferenceKey = preferenceKey;
    }

    public String getPreferenceValue() {
        return preferenceValue;
    }

    public void setPreferenceValue(String preferenceValue) {
        this.preferenceValue = preferenceValue;
    }

    public String getBuyScene() {
        return buyScene;
    }

    public void setBuyScene(String buyScene) {
        this.buyScene = buyScene;
    }

    @Override
    public String toString() {
        return "CustomerPreference{" +
        "pid = " + pid +
        ", customerId = " + customerId +
        ", preferenceKey = " + preferenceKey +
        ", preferenceValue = " + preferenceValue +
        ", buyScene = " + buyScene +
        "}";
    }
}
