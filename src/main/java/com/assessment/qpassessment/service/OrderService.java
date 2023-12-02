package com.assessment.qpassessment.service;

import com.assessment.qpassessment.entity.Order;
import com.assessment.qpassessment.model.OrderDetails;
import com.assessment.qpassessment.model.OrderResponse;
import com.assessment.qpassessment.model.UserRole;

import java.util.List;

public interface OrderService {

    public OrderResponse<List<Order>> getAllOrders(Integer userId);

    public OrderResponse<Order> placeAnOrder(Integer userId, OrderDetails orderDetails);

    public OrderResponse<Order> updateAnOrder(Integer userId, OrderDetails orderDetails);

    public OrderResponse<Order> cancelAnOrder(Integer userId, Integer id);
}
