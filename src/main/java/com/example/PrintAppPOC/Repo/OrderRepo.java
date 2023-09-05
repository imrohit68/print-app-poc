package com.example.PrintAppPOC.Repo;

import com.example.PrintAppPOC.Dtos.OrderDto;
import com.example.PrintAppPOC.Entity.Orders;
import com.example.PrintAppPOC.Entity.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Integer> {
    List<Orders> findByStore(Store store);
}
