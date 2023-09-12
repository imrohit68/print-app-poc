package com.example.PrintAppPOC.Repositories;

import com.example.PrintAppPOC.Entities.Store;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StoreRepo extends JpaRepository<Store,String> {
}
