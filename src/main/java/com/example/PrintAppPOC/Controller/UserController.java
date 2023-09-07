package com.example.PrintAppPOC.Controller;

import com.example.PrintAppPOC.Dtos.UserDto;
import com.example.PrintAppPOC.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {
    private final UserService userService;
    @PostMapping("create")
    public ResponseEntity<UserDto> createUser(@RequestBody UserDto userDto){
        UserDto userDto1 = userService.createUser(userDto);
        return ResponseEntity.ok(userDto1);
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
}
