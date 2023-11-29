package com.assessment.qpassessment.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
public class OrderIdItemIdCompositeKey implements Serializable {

    @Column(name = "ORDER_ID")
    private Integer orderId;

    @Column(name = "ITEM_ID")
    private Integer itemId;

    public OrderIdItemIdCompositeKey(){};

    public OrderIdItemIdCompositeKey(Integer orderId, Integer itemId) {
        this.orderId = orderId;
        this.itemId = itemId;
    }

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getItemId() {
        return itemId;
    }

    public void setItemId(Integer itemId) {
        this.itemId = itemId;
    }
}
