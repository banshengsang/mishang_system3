package com.mishang.mishang_system.entity;

import java.math.BigDecimal;
import java.util.List;

/**
 * 前端点击「确定」时提交的整单数据
 * 前端下单入参DTO
 */
public class CreateOrderRequest {

    /** 客户主键 customer.id */
    private Integer customerId;

    /** 现金 / 转账 / 赊账 */
    private String paymentType;

    /** 订单商品行 */
    private List<OrderLineRequest> items;

    /** 本单顺带更新的客户偏好（可空） */
    private List<PreferenceLineRequest> preferences;

    public Integer getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Integer customerId) {
        this.customerId = customerId;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
    }

    public List<OrderLineRequest> getItems() {
        return items;
    }

    public void setItems(List<OrderLineRequest> items) {
        this.items = items;
    }

    public List<PreferenceLineRequest> getPreferences() {
        return preferences;
    }

    public void setPreferences(List<PreferenceLineRequest> preferences) {
        this.preferences = preferences;
    }

    /** 单行商品 */
    public static class OrderLineRequest {
        /** product.product_id */
        private Integer productId;
        /** 购买数量 */
        private Integer quantity;
        /**
         * 成交价；不传则按「零售价 × 客户折扣」自动算
         */
        private BigDecimal unitPrice;

        public Integer getProductId() {
            return productId;
        }

        public void setProductId(Integer productId) {
            this.productId = productId;
        }

        public Integer getQuantity() {
            return quantity;
        }

        public void setQuantity(Integer quantity) {
            this.quantity = quantity;
        }

        public BigDecimal getUnitPrice() {
            return unitPrice;
        }

        public void setUnitPrice(BigDecimal unitPrice) {
            this.unitPrice = unitPrice;
        }
    }

    /** 单行偏好 */
    public static class PreferenceLineRequest {
        private String preferenceKey;
        private String preferenceValue;
        /** 自饮 / 商务 / 送礼 */
        private String buyScene;

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
    }
}
