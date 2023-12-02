package com.assessment.qpassessment.service.impl;

import com.assessment.qpassessment.entity.Item;
import com.assessment.qpassessment.entity.Order;
import com.assessment.qpassessment.entity.OrderedItem;
import com.assessment.qpassessment.entity.User;
import com.assessment.qpassessment.model.*;
import com.assessment.qpassessment.repository.OrderRepository;
import com.assessment.qpassessment.repository.OrderedItemsRepository;
import com.assessment.qpassessment.service.OrderService;
import com.assessment.qpassessment.service.UserService;
import com.assessment.qpassessment.utils.OrderResponseMessages;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderedItemsRepository orderedItemsRepository;

    @Autowired
    private UserService userService;

    @Override
    public OrderResponse<List<Order>> getAllOrders(Integer userId) {
        List<Order> orders = null;
        OrderResponse<List<Order>> resp = new OrderResponse<>();
        UserResponse<User> userResponse = userService.getUser(userId);
        try {
            UserRole role = null;
            if (userResponse.getSuccess()) {
                role = userResponse.getResponseBody().getRole();
                if (role.equals(UserRole.ADMIN)) {
                    orders = orderRepository.findAll();
                    resp.setSuccess(true);
                    resp.setResponseMessage(OrderResponseMessages.FOUND_ALL_ORDERS);
                } else if (role.equals(UserRole.USER)) {
                    orders = orderRepository.findByUserId(userId);
                    resp.setSuccess(true);
                    resp.setResponseMessage(OrderResponseMessages.FOUND_ALL_ORDERS_FOR_THIS_USER);
                }
            } else {
                resp.setSuccess(false);
                resp.setResponseMessage(userResponse.getResponseMessage());
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(orders);
        return resp;
    }

    @Override
    @Transactional
    public OrderResponse<Order> placeAnOrder(Integer userId, OrderDetails orderDetails) {
        Order order = null;
        OrderResponse<Order> resp = new OrderResponse<>();
        UserResponse<User> userResponse = userService.getUser(userId);
        try {
            if (userResponse.getSuccess()) {
                Boolean isOrderPossible = checkIfAllItemsAreAvailable(orderDetails.getItems());
                Double orderTotalPrice = calculateOrderPrice(orderDetails.getItems());
                if (isOrderPossible) {
                    order = new Order();
                    order.setUserId(userId);
                    order.setPrice(orderTotalPrice);
                    order.setStatus(OrderStatus.ORDER_REGISTERED);
                    List<OrderedItem> orderedItems = createOrderedItemslist(orderDetails.getItems(), order.getId());
                    orderedItemsRepository.saveAll(orderedItems);
                    orderRepository.save(order);
                    resp.setSuccess(true);
                    resp.setResponseMessage(OrderResponseMessages.ORDER_PLACED);
                } else {
                    resp.setSuccess(false);
                    resp.setResponseMessage(OrderResponseMessages.ORDER_NOT_POSSIBLE);
                }
            } else {
                resp.setSuccess(false);
                resp.setResponseMessage(userResponse.getResponseMessage());
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(order);
        return resp;
    }

    @Override
    @Transactional
    public OrderResponse<Order> updateAnOrder(Integer userId, OrderDetails orderDetails) {
        Order order = null;
        OrderResponse<Order> resp = new OrderResponse<>();
        UserResponse<User> userResponse = userService.getUser(userId);
        try {
            if (userResponse.getSuccess()) {
                if (orderDetails.getId() == null) {
                    resp.setSuccess(false);
                    resp.setResponseMessage(OrderResponseMessages.ID_MISSING);
                } else {
                    Optional<Order> orderOptional = orderRepository.findById(orderDetails.getId());
                    if (orderOptional.isEmpty()) {
                        resp.setSuccess(false);
                        resp.setResponseMessage(OrderResponseMessages.INVALID_ID);
                    } else {
                        order = orderOptional.get();
                        if (!order.getUserId().equals(userId)) {
                            resp.setSuccess(false);
                            resp.setResponseMessage(OrderResponseMessages.NOT_ALLOWED_TO_UPDATE_ORDER);
                        } else {
                            List<OrderedItem> existingOrderedItemsList = orderedItemsRepository.findByOrderId(order.getId());
                            List<ItemDetails> deltaItemDetails = findDeltaItems(orderDetails.getItems(), existingOrderedItemsList);
                            if (!deltaItemDetails.isEmpty()) {
                                Boolean isDeltaOrderPossible = checkIfAllItemsAreAvailable(deltaItemDetails);
                                Double deltaOrderTotalPrice = calculateOrderPrice(deltaItemDetails);
                                if (isDeltaOrderPossible) {
                                    for (ItemDetails itemDetails : deltaItemDetails) {
                                        if (itemDetails.getQuantity() > 0) orderedItemsRepository.updateOrderedItems(order.getId(), itemDetails.getId(), itemDetails.getQuantity(), itemDetails.getPrice());
                                        else orderedItemsRepository.updateOrderedItems(order.getId(), itemDetails.getId(), itemDetails.getQuantity());
                                    }
                                    order.setPrice(order.getPrice() + deltaOrderTotalPrice);
                                } else {
                                    resp.setSuccess(false);
                                    resp.setResponseMessage(OrderResponseMessages.ORDER_NOT_POSSIBLE);
                                }
                            }
                            order.setStatus(orderDetails.getStatus());
                            orderRepository.save(order);
                            resp.setSuccess(true);
                            resp.setResponseMessage(OrderResponseMessages.ORDER_UPDATED);
                        }
                    }
                }
            } else {
                resp.setSuccess(false);
                resp.setResponseMessage(userResponse.getResponseMessage());
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(order);
        return resp;
    }

    @Override
    @Transactional
    public OrderResponse<Order> cancelAnOrder(Integer userId, Integer id) {
        Order order = null;
        OrderResponse<Order> resp = new OrderResponse<>();
        UserResponse<User> userResponse = userService.getUser(userId);
        try {
            if (userResponse.getSuccess()) {
                if (id == null) {
                    resp.setSuccess(false);
                    resp.setResponseMessage(OrderResponseMessages.ID_MISSING);
                } else {
                    Optional<Order> orderOptional = orderRepository.findById(id);
                    if (orderOptional.isEmpty()) {
                        resp.setSuccess(false);
                        resp.setResponseMessage(OrderResponseMessages.INVALID_ID);
                    } else {
                        order = orderOptional.get();
                        if (!order.getUserId().equals(userId)) {
                            resp.setSuccess(false);
                            resp.setResponseMessage(OrderResponseMessages.NOT_ALLOWED_TO_CANCEL_ORDER);
                        } else {
                            orderedItemsRepository.deleteAllByOrderId(id);
                            order.setStatus(OrderStatus.ORDER_CANCELLED);
                            orderRepository.save(order);
                            resp.setSuccess(true);
                            resp.setResponseMessage(OrderResponseMessages.ORDER_CANCELLED);
                        }
                    }
                }
            } else {
                resp.setSuccess(false);
                resp.setResponseMessage(userResponse.getResponseMessage());
            }
        } catch (Exception e) {
            resp.setSuccess(false);
            resp.setResponseMessage("Exception Encountered : " + e.getMessage());
        }
        resp.setResponseBody(order);
        return resp;
    }

    private Boolean checkIfAllItemsAreAvailable(List<ItemDetails> items) {
        return null; --
    }

    private Double calculateOrderPrice(List<ItemDetails> items) {
        double totalPrice = 0d;
        for (ItemDetails itemDetails : items) totalPrice += itemDetails.getQuantity() * itemDetails.getPrice();
        return totalPrice;
    }

    private List<OrderedItem> createOrderedItemslist(List<ItemDetails> items, Integer orderId) {
        List<OrderedItem> orderedItems = new ArrayList<>();
        for (ItemDetails itemDetails : items) {
            OrderedItem orderedItem = new OrderedItem();
            orderedItem.setOrderId(orderId);
            orderedItem.setItemId(itemDetails.getId());
            orderedItem.setItemPrice(itemDetails.getPrice());
            orderedItem.setItemQuantity(itemDetails.getQuantity());
            orderedItems.add(orderedItem);
        }
        return orderedItems;
    }

    private List<ItemDetails> findDeltaItems(List<ItemDetails> newItems, List<OrderedItem> existingItems) {
        // negative change in quantity, take the previous price
        // positive change in quantity, take the previous price
        List<ItemDetails> deltaItems = new ArrayList<>();
        newItems.sort((o1, o2) -> o1.getId().compareTo(o2.getId()));
        existingItems.sort((o1, o2) -> o1.getItemId().compareTo(o2.getItemId()));
        int i = 0, j = 0;
        while (i < newItems.size() || j < existingItems.size()) {
            ItemDetails newItemDetails = newItems.get(i);
            OrderedItem existingItem = existingItems.get(j);
            if (newItemDetails.getId().equals(existingItem.getItemId())) {
                // compare
                ItemDetails deltaItemDetails = new ItemDetails();
                deltaItemDetails.setId(newItemDetails.getId());
                deltaItemDetails.setName(newItemDetails.getName());
                int quantityChange = newItemDetails.getQuantity() - existingItem.getItemQuantity();
                deltaItemDetails.setQuantity(quantityChange);
                if (quantityChange != 0) {
                  if (quantityChange < 0) deltaItemDetails.setPrice(existingItem.getItemPrice());
                  else deltaItemDetails.setPrice(newItemDetails.getPrice());
                  deltaItems.add(deltaItemDetails);
                }
                i++;
                j++;
            } else {
                deltaItems.add(newItemDetails);
                i++;
            }
        }
    return deltaItems;
    }
}
