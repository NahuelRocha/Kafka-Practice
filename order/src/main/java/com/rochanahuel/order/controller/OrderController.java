package com.rochanahuel.order.controller;

import com.rochanahuel.order.dto.OrderRequest;
import com.rochanahuel.order.dto.OrderResponse;
import com.rochanahuel.order.service.orderServiceImpl.OrderServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {

    private final OrderServiceImpl orderService;

    @PostMapping("/create")
    public ResponseEntity<OrderResponse> createOrder(@RequestBody OrderRequest orderRequest) {

        return ResponseEntity.ok(orderService.createOrder(orderRequest));
    }

    @DeleteMapping("/cancel/{id}")
    public ResponseEntity<String> cancelOrder(@PathVariable("id") Long id) {

        return ResponseEntity.ok(orderService.cancelOrder(id));
    }
}
