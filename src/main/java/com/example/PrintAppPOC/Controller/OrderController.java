package com.example.PrintAppPOC.Controller;

import com.example.PrintAppPOC.Dtos.OrderDto;
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

    @PostMapping("create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderDto orderDto){
        OrderDto order = orderService.createOrder(orderDto);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }
    @PutMapping("update/{orderId}")
    public ResponseEntity<OrderDto> updateOrder(@RequestBody OrderDto orderDto,@PathVariable Integer orderId){
        OrderDto orderDto1 = orderService.updateOrder(orderDto, orderId);
        return new ResponseEntity<>(orderDto1,HttpStatus.OK);
    }
    @GetMapping("getAll")
    public ResponseEntity<List<OrderDto>> getAllOrders(){
        List<OrderDto> allOrder = orderService.getAllOrder();
        return new ResponseEntity<>(allOrder,HttpStatus.OK);
    }
    @GetMapping("getByStore/{storeId}")
    public ResponseEntity<List<OrderDto>> getOrderByStore(@PathVariable Integer storeId){
        List<OrderDto> orderDto = orderService.orderByStore(storeId);
        return new ResponseEntity<>(orderDto,HttpStatus.OK);
    }
    @GetMapping("getById/{orderId}")
    public ResponseEntity<OrderDto> findById(@PathVariable Integer orderId){
        OrderDto orderById = orderService.getOrderById(orderId);
        return new ResponseEntity<>(orderById,HttpStatus.OK);
    }
    @DeleteMapping("delete/{orderId}")
    public String deleteOrder(@PathVariable Integer orderId){
        orderService.deleteOrder(orderId);
        return "Order Deleted Successfully";
    }
}
