package com.yazici.orderservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.yazici.orderservice.model.Type;
import com.yazici.orderservice.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public Type getByOrderNumber(@RequestParam("orderNumber") String orderNumber) {
        return orderService.getOrderByPostCode(orderNumber);
    }
}
