package com.mishang.mishang_system.enums;

import com.mishang.mishang_system.exception.BusinessException;

public enum PaymentType {
    //英文是枚举常量名，中文是展示文本label
    CASH("现金"),
    TRANSFER("转账"),
    CREDIT("赊账");

    private final String label;

    PaymentType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static PaymentType fromLabel(String raw) {
        if (raw == null || raw.isBlank()) {
            throw new BusinessException("请选择支付方式");
        }
        String text = raw.trim();
        for (PaymentType type : values()) {
            if (type.label.equals(text)) {
                return type;
            }
        }
        throw new BusinessException("支付方式只能是：现金、转账、赊账");
    }
}
