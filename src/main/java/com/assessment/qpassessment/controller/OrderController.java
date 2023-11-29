package com.assessment.qpassessment.controller;

import com.assessment.qpassessment.service.OrderService;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/order")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping(path = "")
    public ResponseEnttty<OrderResponse> placeAnorder(@RequestBody OrderDetails orderDetails) {

    }

}
