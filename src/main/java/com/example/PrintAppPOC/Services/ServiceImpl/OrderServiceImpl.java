package com.example.PrintAppPOC.Services.ServiceImpl;

import com.example.PrintAppPOC.DataTransferObjects.OrderDto;
import com.example.PrintAppPOC.Entities.Files;
import com.example.PrintAppPOC.Entities.Orders;
import com.example.PrintAppPOC.Entities.Store;
import com.example.PrintAppPOC.Entities.Users;
import com.example.PrintAppPOC.Exceptions.ResourceNotFoundException;
import com.example.PrintAppPOC.Repositories.FileRepo;
import com.example.PrintAppPOC.Repositories.OrderRepo;
import com.example.PrintAppPOC.Repositories.StoreRepo;
import com.example.PrintAppPOC.Repositories.UserRepo;
import com.example.PrintAppPOC.Requests.OrderFetchRequest;
import com.example.PrintAppPOC.Responses.FetchOrderResponse;
import com.example.PrintAppPOC.Services.OrderService;
import com.razorpay.Order;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderServiceImpl implements OrderService {
    private final OrderRepo orderRepo;
    private final StoreRepo storeRepo;
    private final ModelMapper modelMapper;
    private final UserRepo userRepo;
    private final FileRepo fileRepo;
    @Override
    public OrderDto createOrder(OrderDto orderDto,String storeId,String userId) {
        Orders order = new Orders();
        List<Files> files = new ArrayList<>();
        for (String x:orderDto.getFileNames()){
            Files file = fileRepo.findById(x) .orElseThrow(()-> new ResourceNotFoundException("File","Id",x));
            files.add(file);
        }
        order.setFileNames(files);
        order.setOrderAmount(orderDto.getOrderAmount());
        order.setPaymentId(orderDto.getPaymentId());
        Users users = userRepo.findById(userId)
                .orElseThrow(()-> new ResourceNotFoundException("User","mobileNumber",userId));
        Store store = storeRepo.findById(storeId)
                .orElseThrow(()->new ResourceNotFoundException("Store","storeId",storeId));
        order.setUser(users);
        order.setStore(store);
        order.setLocalDateTime(LocalDateTime.now());
        Orders orders = orderRepo.save(order);
        return modelMapper.map(orders,OrderDto.class);
    }

    @Override
    public OrderDto updateOrder(OrderDto orderDto, Integer id) {
        Orders orders = orderRepo.findById(id).orElseThrow(()-> new ResourceNotFoundException("Order","orderId",id.toString()));
        List<Files> files = new ArrayList<>();
        for (String x:orderDto.getFileNames()){
            Files file = fileRepo.findById(x) .orElseThrow(()-> new ResourceNotFoundException("File","Id",x));
            files.add(file);
        }
        orders.setFileNames(files);
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
    public List<FetchOrderResponse> orderByStore(OrderFetchRequest orderFetchRequest) {
        Pageable pageable = PageRequest.of(orderFetchRequest.getPageNumber(),10);
        Store store = storeRepo.findById(orderFetchRequest.getStoreId())
                .orElseThrow(()->new ResourceNotFoundException("Store","storeId", orderFetchRequest.getStoreId()));
        Page<Orders> orders = orderRepo.findByStore(store,pageable);
        List<Orders> orders1 = orders.stream().toList();
        List<FetchOrderResponse> fetchOrderResponses  = new ArrayList<>();
        for (Orders x : orders1){
            FetchOrderResponse fetchOrderResponse = new FetchOrderResponse(x.getFileNames().stream().map(files -> files.getFileName()).collect(Collectors.toList()), x.getOrderAmount(),x.getUser().getMobileNumber(),x.getLocalDateTime(),x.getUser().getName());
            fetchOrderResponses.add(fetchOrderResponse);
        }
        return fetchOrderResponses;
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
