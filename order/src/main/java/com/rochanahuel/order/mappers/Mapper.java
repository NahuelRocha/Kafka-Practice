package com.rochanahuel.order.mappers;

import com.rochanahuel.order.dto.OrderItemsRequest;
import com.rochanahuel.order.dto.OrderRequest;
import com.rochanahuel.order.dto.OrderResponse;
import com.rochanahuel.order.model.Order;
import com.rochanahuel.order.model.OrderItems;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Component
public class Mapper {

    public Order orderRequestToOrder(OrderRequest orderRequest) {

        var newOrder = new Order();

        Double totalOrder = orderRequest.getOrderItems().stream()
                .map(item -> item.getPrice() * item.getQuantity())
                .reduce(0.0, Double::sum);

        List<OrderItems> orderItems = orderRequest.getOrderItems().stream()
                .map(orderItemsRequest -> orderItemsRequestToOrderItems(orderItemsRequest, newOrder))
                .toList();

        newOrder.setEmailOfUser(orderRequest.getEmailOfUser());
        newOrder.setShippingAddress(orderRequest.getShippingAddress());
        newOrder.setTotal(totalOrder);
        newOrder.setCreationDate(LocalDateTime.now());
        newOrder.setOrderId(UUID.randomUUID().toString());
        newOrder.setOrderItems(orderItems);

        return newOrder;
    }


    public OrderItems orderItemsRequestToOrderItems(OrderItemsRequest orderItemsRequest, Order order) {

        return OrderItems.builder()
                .sku(orderItemsRequest.getSku())
                .price(orderItemsRequest.getPrice())
                .quantity(orderItemsRequest.getQuantity())
                .order(order)
                .build();
    }

    public OrderResponse orderToOrderResponse(Order order) {

        return OrderResponse.builder()
                .id(order.getId())
                .emailOfUser(order.getEmailOfUser())
                .orderId(order.getOrderId())
                .shippingAddress(order.getShippingAddress())
                .creationDate(order.getCreationDate())
                .total(order.getTotal())
                .orderItems(order.getOrderItems())
                .build();
    }

    public OrderItemsRequest orderItemsToOrderItemsRequest(OrderItems orderItems){

        return OrderItemsRequest.builder()
                .sku(orderItems.getSku())
                .price(orderItems.getPrice())
                .quantity(orderItems.getQuantity())
                .build();
    }

}
