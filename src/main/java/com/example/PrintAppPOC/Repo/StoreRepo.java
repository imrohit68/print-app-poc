package com.example.PrintAppPOC.Repo;

import com.example.PrintAppPOC.Entity.Orders;
import com.example.PrintAppPOC.Entity.Store;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StoreRepo extends JpaRepository<Store,Integer> {

}
