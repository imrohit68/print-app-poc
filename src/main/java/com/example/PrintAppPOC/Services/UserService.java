package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.Dtos.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto,String userId);
    UserDto getById(String userId);
    List<UserDto> getAll();
    void  deleteUser(String userId);
    UserDto getByToken(String token);
}
