package com.assessment.qpassessment.model;

import java.util.List;

public class Order {

    List<ItemDetails> items;

    public List<ItemDetails> getItems() {
        return items;
    }

    public void setItems(List<ItemDetails> items) {
        this.items = items;
    }
}
