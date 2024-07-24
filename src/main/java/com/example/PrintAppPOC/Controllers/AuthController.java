package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.DataTransferObjects.*;
import com.example.PrintAppPOC.Exceptions.*;
import com.example.PrintAppPOC.Requests.JwtAuthRequest;
import com.example.PrintAppPOC.Requests.OtpSendRequest;
import com.example.PrintAppPOC.Responses.*;
import com.example.PrintAppPOC.Services.ServiceImpl.OtpService;
import com.example.PrintAppPOC.Services.StoreService;
import com.example.PrintAppPOC.Services.UserService;
import com.example.PrintAppPOC.Security.CustomUserDetailService;
import com.example.PrintAppPOC.Security.JwtTokenHelper;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenHelper jwtTokenHelper;
    private final UserService userService;
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;
    private final StoreService storeService;

    @PostMapping("/login")
    public ResponseEntity<CreateTokenResponse> createToken(@RequestBody JwtAuthRequest request) {
        if (request.getOtp().equals(otpService.getCacheOtp(request.getMobileNumber()))) {
            this.authenticate(request.getMobileNumber());
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getMobileNumber());
            String token = this.jwtTokenHelper.generateToken(userDetails);
            UserDto userDto = userService.getByToken(token);
            Info data = new Info(token, userDto.getName(), userDto.getMobileNumber());
            CreateTokenResponse createTokenResponse = new CreateTokenResponse(false, data);
            otpService.clearOtp(request.getMobileNumber());
            return new ResponseEntity<>(createTokenResponse, HttpStatus.OK);
        } else if (!Objects.equals(request.getOtp(), otpService.getCacheOtp(request.getMobileNumber()))) {
            throw new CantCreateToken("Invalid OTP. Please enter a valid OTP");
        } else {
            throw new CantCreateToken("Something went wrong. Please try again later");
        }
    }

    @PostMapping("/login/store")
    public ResponseEntity<StoreLoginResponse> createTokenStore(@RequestBody JwtAuthRequest request) {
        if (request.getOtp().equals(otpService.getCacheOtp(request.getMobileNumber()))) {
            this.authenticate(request.getMobileNumber());
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getMobileNumber());
            String token = this.jwtTokenHelper.generateToken(userDetails);
            String storeId = this.storeService.getByToken(token);
            return new ResponseEntity<>(new StoreLoginResponse(true, "Glad to see you again", new DataStoreLogin(storeId, token)), HttpStatus.OK);
        } else if (!Objects.equals(request.getOtp(), otpService.getCacheOtp(request.getMobileNumber()))) {
            throw new CantCreateToken("Invalid OTP. Please enter a valid OTP");
        } else {
            throw new UnknownException("Something went wrong. Please try again later");
        }
    }

    @GetMapping()
    public ResponseEntity<HomeResponse> userDetails(@RequestHeader("Authorization") String token) {
        UserDto userDto = userService.getByToken(token.substring(7));
        return new ResponseEntity<>(new HomeResponse(userDto.getMobileNumber(), userDto.getName(), token.substring(7)), HttpStatus.OK);
    }

    //TODO : Check  whether the api is needed
    @PostMapping("/verifyToken")
    public ResponseEntity<StatusResponse> verifyToken(@RequestHeader("Authorization") String token) {
        return new ResponseEntity<>(new StatusResponse("Token Verified", true), HttpStatus.OK);
    }

    @GetMapping("/home/store")
    public ResponseEntity<StoreLoginResponse> storeDetails(@RequestHeader("Authorization") String token) {
        String mobileNumber = storeService.getByToken(token.substring(7));
        return new ResponseEntity<>(new StoreLoginResponse(true, "Glad to see you again", new DataStoreLogin(mobileNumber, token)), HttpStatus.OK);
    }

    @PostMapping("/requestOtp")
    public ResponseEntity<StatusResponse> getOtp(@RequestBody @Valid OtpSendRequest otpSendDto) {
        otpService.generateOtp(otpSendDto.getMobileNumber());
        return ResponseEntity.ok(new StatusResponse("OTP sent successfully", true));
    }

    @PostMapping("/requestOtp/store")
    public ResponseEntity<StatusResponse> getOtpStore(@RequestBody @Valid OtpSendRequest otpSendDto) {
        otpService.generateOtpStore(otpSendDto.getMobileNumber());
        return ResponseEntity.ok(new StatusResponse("OTP sent successfully", true));
    }

    private void authenticate(String username) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username, " ");
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }

    @GetMapping("/logs")
    public List<String> logs() {
        try (Stream<String> lines = Files.lines(Paths.get("application.log"))) {
            return lines.skip(Math.max(0, Files.lines(Paths.get("application.log")).count() - 100)).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
