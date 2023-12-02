package com.assessment.qpassessment.controller;

import com.assessment.qpassessment.entity.Order;
import com.assessment.qpassessment.model.OrderDetails;
import com.assessment.qpassessment.model.OrderResponse;
import com.assessment.qpassessment.model.UserRole;
import com.assessment.qpassessment.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @GetMapping(path = "/{user_id}")
    public ResponseEntity<OrderResponse<List<Order>>> getAllOrders(@PathVariable(name = "user_id", required = true) Integer userId) {
        OrderResponse<List<Order>> resp = null;
        try {
            resp = orderService.getAllOrders(userId);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new OrderResponse<List<Order>>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }

    }

    @PostMapping(path = "/")
    public ResponseEntity<OrderResponse<Order>> addOrder(@RequestBody OrderDetails orderDetails,
                                                         @PathVariable(name = "user_id", required = true) Integer userId) {
        OrderResponse<Order> resp = null;
        try {
            resp = orderService.placeAnOrder(userId, orderDetails);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new OrderResponse<Order>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PutMapping(path = "/update")
    public ResponseEntity<OrderResponse<Order>> updateOrder(@RequestBody OrderDetails orderDetails,
                                                            @PathVariable(name = "user_id", required = true) Integer userId) {
        OrderResponse<Order> resp = null;
        try {
            resp = orderService.updateAnOrder(userId, orderDetails);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new OrderResponse<Order>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

    @PutMapping(path = "/cancel/{id}")
    public ResponseEntity<OrderResponse<Order>> cancelOrder(@PathVariable(name = "id", required = true) Integer id,
                                                            @PathVariable(name = "user_id", required = true) Integer userId) {
        OrderResponse<Order> resp = null;
        try {
            resp = orderService.cancelAnOrder(userId, id);
            return ResponseEntity.status(HttpStatus.OK).body(resp);
        } catch (Exception e) {
            resp = new OrderResponse<Order>();
            resp.setSuccess(false);
            resp.setResponseMessage(e.getMessage());
            resp.setResponseBody(null);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(resp);
        }
    }

}
