package com.example.PrintAppPOC.security;

import com.example.PrintAppPOC.Entity.Users;
import com.example.PrintAppPOC.Repo.UserRepo;
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
        if(userRepo.existsUsersByMobileNumber(username)){
            Users user =this.userRepo.findById(username)
                    .orElseThrow();
            return user;
        }
        else {
            Users users = new Users();
            users.setMobileNumber(username);
            return users;
        }
    }
}
