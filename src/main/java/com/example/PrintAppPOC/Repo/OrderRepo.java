package com.example.PrintAppPOC.Repo;

import com.example.PrintAppPOC.Entity.Orders;
import com.example.PrintAppPOC.Entity.Store;
import com.example.PrintAppPOC.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface OrderRepo extends JpaRepository<Orders,Integer> {
    List<Orders> findByStore(Store store);
    List<Orders> findByUser(Users user);
}
