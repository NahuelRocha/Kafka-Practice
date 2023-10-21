package com.rochanahuel.order.service;

import com.rochanahuel.order.dto.OrderRequest;
import com.rochanahuel.order.dto.OrderResponse;

public interface OrderService {

    OrderResponse createOrder(OrderRequest orderRequest);

    String cancelOrder(Long id);
}
