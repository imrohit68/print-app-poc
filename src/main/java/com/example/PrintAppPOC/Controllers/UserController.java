package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.DataTransferObjects.UserDto;
import com.example.PrintAppPOC.Exceptions.ResourceNotFoundException;
import com.example.PrintAppPOC.Exceptions.UsernameConstraintException;
import com.example.PrintAppPOC.Responses.CreateUserWithTokenResponse;
import com.example.PrintAppPOC.Security.CustomUserDetailService;
import com.example.PrintAppPOC.Security.JwtTokenHelper;
import com.example.PrintAppPOC.Services.UserService;
import jakarta.validation.ConstraintViolationException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.TransactionSystemException;
import org.springframework.web.ErrorResponseException;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    private final JwtTokenHelper jwtTokenHelper;
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;
    @PostMapping("/create")
    public ResponseEntity<CreateUserWithTokenResponse> createUser(@RequestBody UserDto userDto){
        try {
            userService.createUser(userDto);
        }
        catch (TransactionSystemException ex){
            throw new UsernameConstraintException("Invalid Username. The username must be between 2 to 20 characters.");
        }
        this.authenticate(userDto.getMobileNumber());
        UserDetails userDetails = this.customUserDetailService.loadUserByUsername(userDto.getMobileNumber());
        String token = this.jwtTokenHelper.generateToken(userDetails);
        return new ResponseEntity<>(new CreateUserWithTokenResponse(true,token,"Welcome Onboard"), HttpStatus.CREATED);
    }
    @PutMapping("updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser(@RequestBody UserDto userDto,@PathVariable String userId){
        UserDto userDto1 = userService.updateUser(userDto,userId);
        return ResponseEntity.ok(userDto1);
    }
    @GetMapping("getById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId){
        UserDto userDto = userService.getById(userId);
        return ResponseEntity.ok(userDto);
    }
    @GetMapping("getAllUser")
    public ResponseEntity<List<UserDto>> getAllUser(){
        List<UserDto> userDto = userService.getAll();
        return ResponseEntity.ok(userDto);
    }
    @DeleteMapping("deleteUser/{userId}")
    public String deleteUser(@PathVariable String userId){
        userService.deleteUser(userId);
        return "Deleted Successfully";
    }
    private void authenticate(String username) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username," ");
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
