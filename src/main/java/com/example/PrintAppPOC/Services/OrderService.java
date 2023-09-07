package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.Dtos.OrderDto;

import java.util.List;


public interface OrderService {
    OrderDto createOrder(OrderDto orderDto,String storeId,String userId);
    OrderDto updateOrder(OrderDto orderDto,Integer id);
    OrderDto getOrderById(Integer orderId);
    List<OrderDto> getAllOrder();
    void deleteOrder(Integer orderId);
    List<OrderDto> orderByStore(String storeId);
    List<OrderDto> orderByUser(String userId);
}
