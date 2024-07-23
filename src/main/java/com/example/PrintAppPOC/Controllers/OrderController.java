package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.DataTransferObjects.OrderDto;
import com.example.PrintAppPOC.Requests.OrderFetchRequest;
import com.example.PrintAppPOC.Requests.OrderRequest;
import com.example.PrintAppPOC.Requests.OrderStatusUpdateRequest;
import com.example.PrintAppPOC.Responses.FetchOrderResponse;
import com.example.PrintAppPOC.Responses.StatusResponse;
import com.example.PrintAppPOC.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/order")
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/create")
    public ResponseEntity<StatusResponse> createOrder(@RequestBody OrderDto orderDto, @RequestHeader("Authorization") String token){
        OrderDto order = orderService.createOrder(orderDto,orderDto.getStoreId(),orderDto.getUserId());
        return new ResponseEntity<>(new StatusResponse("Order Placed Successfully",true), HttpStatus.CREATED);
    }
    @PutMapping("update/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto,@PathVariable Integer orderId,@RequestHeader("Authorization") String token){
        OrderDto orderDto1 = orderService.updateOrder(orderDto, orderId);
        return new ResponseEntity<>(orderDto1,HttpStatus.OK);
    }
    @GetMapping("getAll")
    public ResponseEntity<List<OrderDto>> getAllOrders(@RequestHeader("Authorization") String token){
        List<OrderDto> allOrder = orderService.getAllOrder();
        return new ResponseEntity<>(allOrder,HttpStatus.OK);
    }
    @PostMapping("getByStore")
    public ResponseEntity<List<FetchOrderResponse>> getOrderByStore(@RequestBody OrderFetchRequest orderFetchRequest, @RequestHeader("Authorization") String token){
        List<FetchOrderResponse> orderResponses = orderService.orderByStore(orderFetchRequest);
        return new ResponseEntity<>(orderResponses,HttpStatus.OK);
    }
    @PostMapping("/updateOrderStatus")
    public ResponseEntity<StatusResponse> updateOrderStatus(@RequestBody OrderStatusUpdateRequest orderStatusUpdateRequest){
        OrderDto orderDto = orderService.updateOrderToCompleted(orderStatusUpdateRequest.getId());
        return new ResponseEntity<>(new StatusResponse(String.format("Order status updated to : %s",orderDto.getOrderStatus()),true),HttpStatus.OK);
    }
    @PostMapping("getByUser")
    public ResponseEntity<List<OrderDto>> getOrderByUser(@RequestBody OrderRequest orderRequest, @RequestHeader("Authorization") String token){
        List<OrderDto> orderDto = orderService.orderByUser(orderRequest.getId());
        return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }
    @GetMapping("getById/{orderId}")
    public ResponseEntity<OrderDto> findById(@PathVariable Integer orderId,@RequestHeader("Authorization") String token){
        OrderDto orderById = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orderById,HttpStatus.OK);
    }
    @DeleteMapping("delete/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId,@RequestHeader("Authorization") String token){
        orderService.deleteOrder(orderId);
        return "Order Deleted Successfully";
    }
}
