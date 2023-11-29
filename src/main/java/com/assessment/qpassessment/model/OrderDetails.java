package com.assessment.qpassessment.model;

import java.util.List;

public class OrderDetails {

    private Integer id;
    private List<ItemDetails> items;

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
}
