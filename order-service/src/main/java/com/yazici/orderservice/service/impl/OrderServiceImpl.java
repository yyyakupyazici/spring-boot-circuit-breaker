package com.yazici.orderservice.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import com.yazici.orderservice.dto.AddressDTO;
import com.yazici.orderservice.model.Failure;
import com.yazici.orderservice.model.Order;
import com.yazici.orderservice.model.Type;
import com.yazici.orderservice.repository.OrderRepository;
import com.yazici.orderservice.service.OrderService;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderRepository orderRepository;
    @Autowired
    private RestTemplate restTemplate;
    private static final String SERVICE_NAME = "order-service";
    private static final String ADDRESS_SERVICE_URL = "http://localhost:9090/addresses/";

    @CircuitBreaker(name = SERVICE_NAME, fallbackMethod = "fallbackMethod")
    public Type getOrderByPostCode(String orderNumber) {
        Order order = orderRepository.findByOrderNumber(orderNumber)
                .orElseThrow(() -> new RuntimeException("Order Not Found: " + orderNumber));
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        HttpEntity<AddressDTO> entity = new HttpEntity<>(null, headers);
        ResponseEntity<AddressDTO> response =
                restTemplate.exchange((ADDRESS_SERVICE_URL + order.getPostalCode()), HttpMethod.GET,
                        entity, AddressDTO.class);
        AddressDTO addressDTO = response.getBody();
        if (addressDTO != null) {
            order.setShippingState(addressDTO.getState());
            order.setShippingCity(addressDTO.getCity());
        }
        return order;
    }

    private Type fallbackMethod(Exception e) {
        return new Failure("Address service is not responding properly");
    }
}
