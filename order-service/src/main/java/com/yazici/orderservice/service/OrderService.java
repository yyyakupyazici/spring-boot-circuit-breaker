package com.yazici.orderservice.service;

import com.yazici.orderservice.model.Type;

public interface OrderService {
    Type getOrderByPostCode(String orderNumber);
}
