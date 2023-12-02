package com.assessment.qpassessment.model;

import com.assessment.qpassessment.service.OrderService;

import java.util.List;

public class OrderDetails {

    private Integer id;
    private List<ItemDetails> items;
    private OrderStatus status;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public List<ItemDetails> getItems() {
        return items;
    }

    public void setItems(List<ItemDetails> items) {
        this.items = items;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
