package com.microserviceexample.orderservice.service;

import com.microserviceexample.orderservice.dto.InventoryResponse;
import com.microserviceexample.orderservice.dto.OrderLineItemsDto;
import com.microserviceexample.orderservice.dto.OrderRequest;
import com.microserviceexample.orderservice.model.Order;
import com.microserviceexample.orderservice.model.OrderLinesItems;
import com.microserviceexample.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@Transactional
public class OrderService {

    @Autowired
    private  OrderRepository orderRepository;

    @Autowired
    private WebClient webClient;
    public void  placeOrder(OrderRequest orderRequest){
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderLinesItems> orderLinesItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLinesItemsList(orderLinesItems);

        List<String> skuCodes = order.getOrderLinesItemsList().stream().map(OrderLinesItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/inventory",
                        uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();
        boolean allProductStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);

        if (allProductStock){
                orderRepository.save(order);
        }
        else {
            throw new IllegalArgumentException("Product is not in stock, please try again later again");
        }
    }

    private OrderLinesItems mapToDto(OrderLineItemsDto orderLineItemDto){
        OrderLinesItems orderLinesItems = new OrderLinesItems();
        orderLinesItems.setPrice(orderLineItemDto.getPrice());
        orderLinesItems.setQuantity(orderLineItemDto.getQuantity());
        orderLinesItems.setSkuCode(orderLineItemDto.getSkuCode());
        return  orderLinesItems;

    }

}
