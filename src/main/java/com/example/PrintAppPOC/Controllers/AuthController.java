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
        boolean status = otpService.verifyOtp(request.getMobileNumber(), request.getOtp());
        if (!status) {
            throw new CantCreateToken("Invalid OTP. Please enter a valid OTP");
        } else {
            this.authenticate(request.getMobileNumber());
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getMobileNumber());
            String token = this.jwtTokenHelper.generateToken(userDetails);
            UserDto userDto = userService.getByToken(token);
            Info data = new Info(userDto.getName(),token,userDto.getMobileNumber());
            CreateTokenResponse createTokenResponse = new CreateTokenResponse(false, data);
            return new ResponseEntity<>(createTokenResponse, HttpStatus.OK);
        }
    }

    @PostMapping("/login/store")
    public ResponseEntity<StoreLoginResponse> createTokenStore(@RequestBody JwtAuthRequest request) {
        boolean status = otpService.verifyOtp(request.getMobileNumber(), request.getOtp());
        if (!status) {
            throw new CantCreateToken("Invalid OTP. Please enter a valid OTP");
        } else {
            this.authenticate(request.getMobileNumber());
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getMobileNumber());
            String token = this.jwtTokenHelper.generateToken(userDetails);
            String storeId = this.storeService.getByToken(token);
            return new ResponseEntity<>(new StoreLoginResponse(true, "Glad to see you again", new DataStoreLogin(storeId, token)), HttpStatus.OK);
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
        return ResponseEntity.ok(new StatusResponse(otpService.generateOtp(otpSendDto.getMobileNumber()), true));
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

}
