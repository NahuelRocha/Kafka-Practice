package com.rochanahuel.order.dto;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequest {

    private String emailOfUser;
    private String shippingAddress;
    private List<OrderItemsRequest> orderItems;
}
