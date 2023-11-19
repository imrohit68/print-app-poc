package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.DataTransferObjects.UserDto;
import com.example.PrintAppPOC.Exceptions.InvalidTokenException;
import com.example.PrintAppPOC.Exceptions.ResourceNotFoundException;
import com.example.PrintAppPOC.Exceptions.UnknownException;
import com.example.PrintAppPOC.Exceptions.UsernameConstraintException;
import com.example.PrintAppPOC.Responses.CreateUserWithTokenResponse;
import com.example.PrintAppPOC.Responses.StatusResponse;
import com.example.PrintAppPOC.Security.CustomUserDetailService;
import com.example.PrintAppPOC.Security.JwtTokenHelper;
import com.example.PrintAppPOC.Services.UserService;
import com.twilio.base.Resource;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.TransactionSystemException;
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
    @PutMapping("updateUser")
    public ResponseEntity<StatusResponse> updateUser(@RequestBody UserDto userDto,@RequestHeader("Authorization") String token){
        try {
            userService.updateUser(userDto);
            return ResponseEntity.ok(new StatusResponse("Successfully Updated User",true));
        }
        catch (ResourceNotFoundException ex){
            throw ex;
        }
        catch (TransactionSystemException ex){
            throw new UsernameConstraintException("Invalid Username. The username must be between 2 to 20 characters.");
        }
        catch (Exception e){
            throw new UnknownException(e.getMessage());
        }
    }
    @GetMapping("getById/{userId}")
    public ResponseEntity<UserDto> getUserById(@PathVariable String userId,@RequestHeader("Authorization") String token){
        UserDto userDto = userService.getById(userId);
        return ResponseEntity.ok(userDto);
    }
    @GetMapping("getAllUser")
    public ResponseEntity<List<UserDto>> getAllUser(@RequestHeader("Authorization") String token){
        List<UserDto> userDto = userService.getAll();
        return ResponseEntity.ok(userDto);
    }
    @DeleteMapping("deleteUser/{userId}")
    public String deleteUser(@PathVariable String userId,@RequestHeader("Authorization") String token){
        userService.deleteUser(userId);
        return "Deleted Successfully";
    }
    private void authenticate(String username) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username," ");
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
