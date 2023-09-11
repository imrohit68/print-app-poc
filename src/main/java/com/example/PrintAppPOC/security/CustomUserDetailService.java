package com.example.PrintAppPOC.security;

import com.example.PrintAppPOC.Entity.Users;
import com.example.PrintAppPOC.Exception.ResourceNotFoundException;
import com.example.PrintAppPOC.Repo.UserRepo;
import com.example.PrintAppPOC.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CustomUserDetailService implements UserDetailsService {

    private final UserRepo userRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Users user =this.userRepo.findById(username)
                .orElseThrow(()->new ResourceNotFoundException("User","mobileNumber",username));
                return user;
    }
}
