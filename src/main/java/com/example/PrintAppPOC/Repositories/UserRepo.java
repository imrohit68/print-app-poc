package com.example.PrintAppPOC.Repositories;

import com.example.PrintAppPOC.Entities.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,String> {
}