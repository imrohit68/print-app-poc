package com.example.PrintAppPOC.Services.ServiceImpl;

import com.example.PrintAppPOC.Dtos.UserDto;
import com.example.PrintAppPOC.Entity.Users;
import com.example.PrintAppPOC.Exception.MobileNumberValidationException;
import com.example.PrintAppPOC.Exception.ResourceNotFoundException;
import com.example.PrintAppPOC.Exception.UserAlreadyExistsException;
import com.example.PrintAppPOC.Repo.UserRepo;
import com.example.PrintAppPOC.Services.UserService;
import com.example.PrintAppPOC.security.JwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class UserImpl implements UserService {
    private final UserRepo userRepo;
    private final ModelMapper modelMapper;
    private final JwtTokenHelper jwtTokenHelper;
    @Override
    public UserDto createUser(UserDto userDto) {
        Users user = modelMapper.map(userDto, Users.class);
        String mobileNumber = userDto.getMobileNumber();
        if(mobileNumber!=null&&mobileNumber.length()!=13){
            throw new MobileNumberValidationException("please enter a valid 10 digit mobileNumber");
        }
        try{
            Users user1 = userRepo.save(user);
            return modelMapper.map(user1,UserDto.class);
        }
        catch (Exception e){
            throw new MobileNumberValidationException("please enter mobileNumber");
        }
    }

    @Override
    public UserDto updateUser(UserDto userDto, String userId) {
        Users user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","mobileNumber",userId));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public UserDto getById(String userId) {
        Users user = userRepo.findById(userId).orElseThrow(()->new ResourceNotFoundException("user","mobileNumber",userId));
        return modelMapper.map(user,UserDto.class);
    }

    @Override
    public List<UserDto> getAll() {
        List<Users> users = userRepo.findAll();
        List<UserDto> userDto = users.stream().map(user -> modelMapper.map(user,UserDto.class)).collect(Collectors.toList());;
        return userDto;
    }

    @Override
    public void deleteUser(String userId) {
        Users user = userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("user","mobileNumber",userId));
        userRepo.delete(user);
    }

    @Override
    public UserDto getByToken(String token) {
        String id = jwtTokenHelper.extractUsername(token);
        Users users = userRepo.findById(id).orElseThrow(()->new ResourceNotFoundException("user","token",token));
        return modelMapper.map(users,UserDto.class);
    }
}
