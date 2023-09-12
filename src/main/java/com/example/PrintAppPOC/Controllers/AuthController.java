package com.example.PrintAppPOC.Controllers;

import com.example.PrintAppPOC.DataTransferObjects.*;
import com.example.PrintAppPOC.Exceptions.CantCreateToken;
import com.example.PrintAppPOC.Exceptions.MobileNumberValidationException;
import com.example.PrintAppPOC.Requests.JwtAuthRequest;
import com.example.PrintAppPOC.Requests.OtpSendRequest;
import com.example.PrintAppPOC.Responses.CreateTokenResponse;
import com.example.PrintAppPOC.Requests.JwtUserRequest;
import com.example.PrintAppPOC.Responses.StatusResponse;
import com.example.PrintAppPOC.Services.ServiceImpl.OtpService;
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
    @PostMapping("/login")
    public ResponseEntity<CreateTokenResponse> createToken(@RequestBody JwtAuthRequest request){
        try {
            if(request.getOtp().equals(otpService.getCacheOtp(request.getMobileNumber()))){
                this.authenticate(request.getMobileNumber());
                UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getMobileNumber());
                String token = this.jwtTokenHelper.generateToken(userDetails);
                UserDto userDto = userService.getByToken(token);
                CreateTokenResponse createTokenResponse = new CreateTokenResponse(false,token,userDto);
                return new ResponseEntity<>(createTokenResponse,HttpStatus.OK);
            }
            else{
                throw new CantCreateToken("Invalid OTP. Please enter a valid OTP");
            }
        }
        catch (Exception e){
            throw new CantCreateToken(e.getMessage());
        }
    }
    @GetMapping()
    public ResponseEntity<UserDto> userDetails(@RequestBody JwtUserRequest token){
        return new ResponseEntity<>(userService.getByToken(token.getToken()),HttpStatus.OK);
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
    private void authenticate(String username) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username," ");
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
