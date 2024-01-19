package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.DataTransferObjects.OrderDto;
import com.example.PrintAppPOC.Entities.Orders;
import com.example.PrintAppPOC.Requests.OrderFetchRequest;
import com.example.PrintAppPOC.Responses.FetchOrderResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;


public interface OrderService {
    OrderDto createOrder(OrderDto orderDto,String storeId,String userId);
    OrderDto updateOrder(OrderDto orderDto,Integer id);
    OrderDto getOrderById(Integer orderId);
    List<OrderDto> getAllOrder();
    void deleteOrder(Integer orderId);
    List<FetchOrderResponse> orderByStore(OrderFetchRequest orderFetchRequest);
    List<OrderDto> orderByUser(String userId);
}
