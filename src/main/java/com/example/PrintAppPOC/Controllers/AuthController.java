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
    public ResponseEntity<CreateTokenResponse> createToken(@RequestBody JwtAuthRequest request){
            if(request.getOtp().equals(otpService.getCacheOtp(request.getMobileNumber()))){
                this.authenticate(request.getMobileNumber());
                UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getMobileNumber());
                String token = this.jwtTokenHelper.generateToken(userDetails);
                UserDto userDto = userService.getByToken(token);
                Info data = new Info();
                data.setToken(token);
                data.setUserName(userDto.getName());
                data.setMobileNumber(userDto.getMobileNumber());
                CreateTokenResponse createTokenResponse = new CreateTokenResponse(false,data);
                return new ResponseEntity<>(createTokenResponse,HttpStatus.OK);
            }
            else if(request.getOtp()!=(otpService.getCacheOtp(request.getMobileNumber()))){
                throw new CantCreateToken("Invalid OTP. Please enter a valid OTP");
            }
            else{
                throw new CantCreateToken("Something went wrong. Please try again later");
            }
    }
    @PostMapping("/login/store")
    public ResponseEntity<StoreLoginResponse> createTokenStore(@RequestBody JwtAuthRequest request){
        if(request.getOtp().equals(otpService.getCacheOtp(request.getMobileNumber()))){
            this.authenticate(request.getMobileNumber());
            UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getMobileNumber());
            String token = this.jwtTokenHelper.generateToken(userDetails);
            String storeId = this.storeService.getByToken(token);
            return new ResponseEntity<>(new StoreLoginResponse(true,"Glad to see you again",new DataStoreLogin(storeId,token)),HttpStatus.OK);
        }
        else if(request.getOtp()!=(otpService.getCacheOtp(request.getMobileNumber()))){
            throw new CantCreateToken("Invalid OTP. Please enter a valid OTP");
        }
        else{
            throw new UnknownException("Something went wrong. Please try again later");
        }
    }
    @GetMapping()
    public ResponseEntity<HomeResponse> userDetails(@RequestHeader("Authorization") String token){
        try{
            UserDto userDto = userService.getByToken(token.substring(7));
            return new ResponseEntity<>(new HomeResponse(userDto.getMobileNumber(),userDto.getName(),token.substring(7)),HttpStatus.OK);
        }catch (Exception e){
            System.out.println(e.getMessage());
        }
        return null;
    }
    @PostMapping("/verifyToken")
    public ResponseEntity<StatusResponse> verifyToken(@RequestHeader("Authorization") String token){
        if(token==null){
            return new ResponseEntity<>(new StatusResponse("Token is Null",false),HttpStatus.UNAUTHORIZED);
        }
        return new ResponseEntity<>(new StatusResponse("Token Verified",true),HttpStatus.OK);
    }
    @GetMapping("home/store")
    public ResponseEntity<StoreLoginResponse>storeDetails(@RequestHeader("Authorization") String token){
        String mobileNumber = storeService.getByToken(token.substring(7));
        return new ResponseEntity<>(new StoreLoginResponse(true,"Glad to see you again",new DataStoreLogin(mobileNumber,token)),HttpStatus.OK);
    }
    @PostMapping ( "/requestOtp")
    public ResponseEntity<StatusResponse> getOtp(@RequestBody OtpSendRequest otpSendDto){
        String  mobileNumber = otpSendDto.getMobileNumber();
        if(mobileNumber==null){
            throw new MobileNumberValidationException("Please enter mobile number");
        }
        if(mobileNumber.length()!=13){
            throw new MobileNumberValidationException("Please enter a valid 10 digit mobile number");
        }
        try{
            otpService.generateOtp(otpSendDto.getMobileNumber());
            return ResponseEntity.ok(new StatusResponse("OTP sent successfully",true));

        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new StatusResponse("Something went wrong. Please try again later",false),HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    @PostMapping ( "/requestOtp/store")
    public ResponseEntity<StatusResponse> getOtpStore(@RequestBody OtpSendRequest otpSendDto){
        String  mobileNumber = otpSendDto.getMobileNumber();
        if(mobileNumber==null){
            throw new MobileNumberValidationException("Please enter mobile number");
        }
        if(mobileNumber.length()!=13){
            throw new MobileNumberValidationException("Please enter a valid 10 digit mobile number");
        }
        try{
            UserDto userDto = userService.getById(mobileNumber);
            if(userDto.getStore()==null){
                throw new StoreDoesNotExist();
            }
            otpService.generateOtp(otpSendDto.getMobileNumber());
            return ResponseEntity.ok(new StatusResponse("OTP sent successfully",true));

        }catch (StoreDoesNotExist e){
            throw  e;
        }
        catch (ResourceNotFoundException e){
            throw new StoreDoesNotExist();
        }
        catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new StatusResponse("Something went wrong. Please try again later",false),HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    private void authenticate(String username) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username," ");
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
