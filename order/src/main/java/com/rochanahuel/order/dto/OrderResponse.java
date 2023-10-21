package com.rochanahuel.order.dto;

import com.rochanahuel.order.model.OrderItems;
import lombok.Builder;

import java.time.LocalDateTime;
import java.util.List;

@Builder
public record OrderResponse (
        Long id,
        String emailOfUser,
        String orderId,
        String shippingAddress,
        LocalDateTime creationDate,
        Double total,
        List<OrderItems> orderItems

){}
