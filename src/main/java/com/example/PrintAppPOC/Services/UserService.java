package com.example.PrintAppPOC.Services;

import com.example.PrintAppPOC.DataTransferObjects.UserDto;

import java.util.List;

public interface UserService {
    UserDto createUser(UserDto userDto);
    UserDto updateUser(UserDto userDto);
    UserDto getById(String userId);
    List<UserDto> getAll();
    void  deleteUser(String userId);
    UserDto getByToken(String token);
}
