package com.example.PrintAppPOC.Services.ServiceImpl;

import com.example.PrintAppPOC.DataTransferObjects.OrderDto;
import com.example.PrintAppPOC.Entities.Orders;
import com.example.PrintAppPOC.Entities.Store;
import com.example.PrintAppPOC.Entities.Users;
import com.example.PrintAppPOC.Exceptions.ResourceNotFoundException;
import com.example.PrintAppPOC.Repositories.OrderRepo;
import com.example.PrintAppPOC.Repositories.StoreRepo;
import com.example.PrintAppPOC.Repositories.UserRepo;
import com.example.PrintAppPOC.Services.OrderService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final StoreRepo storeRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    @Override
    public OrderDto createOrder(OrderDto orderDto,String storeId,String userId) {
        Orders order = modelMapper.map(orderDto,Orders.class);
        Users users = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","mobileNumber",userId));
        Store store = storeRepo.findById(storeId)
                .orElseThrow(()->new ResourceNotFoundException("Store","storeId",storeId));
        order.setUser(users);
        order.setStore(store);
        Orders orders = orderRepo.save(order);
        return modelMapper.map(orders,OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Integer id) {
        Orders orders = orderRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order","orderId",id.toString()));
        orders.setFileNames(orderDto.getFileNames());
        return modelMapper.map(orders,OrderDto.class);
    }

    @Override
    public OrderDto getOrderById(Integer orderId) {
        Orders orders = orderRepo.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order","orderId",orderId.toString()));
        return modelMapper.map(orders,OrderDto.class);
    }

    @Override
    public List<OrderDto> getAllOrder() {
        List<Orders> orders = orderRepo.findAll();
        List<OrderDto> orderDto = orders.stream()
                .map(orders1 -> modelMapper.map(orders1,OrderDto.class)).collect(Collectors.toList());
        return orderDto;

    }

    @Override
    public void deleteOrder(Integer orderId) {
        Orders orders = orderRepo.findById(orderId)
                .orElseThrow(()-> new ResourceNotFoundException("Order","orderId",orderId.toString()));
        orderRepo.delete(orders);
    }

    @Override
    public List<OrderDto> orderByStore(String storeId) {
        Store store = storeRepo.findById(storeId)
                .orElseThrow(()->new ResourceNotFoundException("Store","storeId",storeId));
        List<Orders> orderDto = orderRepo.findByStore(store);
        List<OrderDto> order = orderDto.stream()
                .map(orders -> modelMapper.map(orders,OrderDto.class)).collect(Collectors.toList());
        return order;
    }

    @Override
    public List<OrderDto> orderByUser(String userId) {
        Users user = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","mobileNumber",userId));
        List<Orders> orders = orderRepo.findByUser(user);
        List<OrderDto> orderDto = orders.stream().map(orders1 -> modelMapper.map(orders1,OrderDto.class)).collect(Collectors.toList());
        return orderDto;
    }
}
