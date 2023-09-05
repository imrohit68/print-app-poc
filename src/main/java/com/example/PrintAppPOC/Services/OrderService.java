package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.Dtos.OrderDto;

import java.util.List;


public interface OrderService {
    OrderDto createOrder(OrderDto orderDto);
    OrderDto updateOrder(OrderDto orderDto,Integer id);
    OrderDto getOrderById(Integer orderId);
    List<OrderDto> getAllOrder();
    void deleteOrder(Integer orderId);
    List<OrderDto> orderByStore(Integer storeId);
}
