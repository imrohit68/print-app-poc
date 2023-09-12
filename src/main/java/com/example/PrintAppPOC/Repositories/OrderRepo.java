package com.example.PrintAppPOC.Repositories;

import com.example.PrintAppPOC.Entities.Orders;
import com.example.PrintAppPOC.Entities.Store;
import com.example.PrintAppPOC.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Integer> {
    List<Orders> findByStore(Store store);
    List<Orders> findByUser(Users user);
}
