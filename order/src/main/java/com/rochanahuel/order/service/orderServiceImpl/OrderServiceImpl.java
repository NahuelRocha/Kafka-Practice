package com.rochanahuel.order.service.orderServiceImpl;

import com.rochanahuel.order.dto.OrderItemsRequest;
import com.rochanahuel.order.dto.OrderRequest;
import com.rochanahuel.order.dto.OrderResponse;
import com.rochanahuel.order.exceptions.OrderNotFoundException;
import com.rochanahuel.order.mappers.Mapper;
import com.rochanahuel.order.model.Order;
import com.rochanahuel.order.model.OrderItems;
import com.rochanahuel.order.repository.OrderRepository;
import com.rochanahuel.order.service.Kafka.KafkaProducerService;
import com.rochanahuel.order.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final KafkaProducerService kafkaProducerService;
    private final Mapper mapper;


    @Override
    public OrderResponse createOrder(OrderRequest orderRequest) {

        Order newOrder = mapper.orderRequestToOrder(orderRequest);

        orderRepository.save(newOrder);

        List<OrderItemsRequest> orderItems = orderRequest.getOrderItems();

        for (OrderItemsRequest order : orderItems) {

            UUID uuid = UUID.randomUUID();

            log.info("We received the transaction with the key: " + uuid);

            kafkaProducerService.send("order-created-topic", uuid, order);
        }

        return mapper.orderToOrderResponse(newOrder);
    }

    @Override
    public String cancelOrder(Long id) {

        var order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException("Order not found with id: " + id));

        List<OrderItems> getOrderItems = order.getOrderItems();

        List<OrderItemsRequest> sendForAddStockInventory = getOrderItems.stream()
                .map(mapper::orderItemsToOrderItemsRequest)
                .toList();

        UUID uuid = UUID.randomUUID();

        log.info("We received a transaction with the key: " + uuid);

        for (OrderItemsRequest orderItemsRequest : sendForAddStockInventory){

            kafkaProducerService.send("order-cancel-topic", uuid , orderItemsRequest);
        }


        orderRepository.delete(order);

        return "SUCCESS";
    }
}
