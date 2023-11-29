package com.assessment.qpassessment.entity;

import com.assessment.qpassessment.model.OrderStatus;
import jakarta.persistence.*;

@Entity
@Table(name = "ORDERS")
public class Order {

    @EmbeddedId
    private OrderIdItemIdCompositeKey id;

    @Column(name = "QUANTITY")
    private Integer quantity;

    @Column(name = "PRICE")
    private Double price;

    @Column(name = "STATUS")
    private OrderStatus status;

    public OrderIdItemIdCompositeKey getId() {
        return id;
    }

    public void setId(OrderIdItemIdCompositeKey id) {
        this.id = id;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public void setStatus(OrderStatus status) {
        this.status = status;
    }
}
