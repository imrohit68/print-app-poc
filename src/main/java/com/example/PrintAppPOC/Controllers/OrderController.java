package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.DataTransferObjects.OrderDto;
import com.example.PrintAppPOC.Exceptions.InvalidTokenException;
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

    @PostMapping("create/{storeId}/{userId}")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto,@PathVariable String storeId,@PathVariable String userId,@RequestHeader("Authorization") String token){
        OrderDto order = orderService.createOrder(orderDto,storeId,userId);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
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
    @GetMapping("getByStore/{storeId}")
    public ResponseEntity<List<OrderDto>> getOrderByStore(@PathVariable String storeId,@RequestHeader("Authorization") String token){
        List<OrderDto> orderDto = orderService.orderByStore(storeId);
        return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }
    @GetMapping("getByUser/{userId}")
    public ResponseEntity<List<OrderDto>> getOrderByUser(@PathVariable String userId,@RequestHeader("Authorization") String token){
        List<OrderDto> orderDto = orderService.orderByUser(userId);
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
