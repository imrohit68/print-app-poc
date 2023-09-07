package com.example.PrintAppPOC.Repo;

import com.example.PrintAppPOC.Entity.Users;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<Users,String> {
    boolean existsUsersByMobileNumber(String mobileNumber);
}
