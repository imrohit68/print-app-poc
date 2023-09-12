package com.example.PrintAppPOC.Controller;

import com.example.PrintAppPOC.Dtos.*;
import com.example.PrintAppPOC.Exception.CantCreateToken;
import com.example.PrintAppPOC.Exception.MobileNumberValidationException;
import com.example.PrintAppPOC.Services.ServiceImpl.OtpService;
import com.example.PrintAppPOC.Services.UserService;
import com.example.PrintAppPOC.security.CustomUserDetailService;
import com.example.PrintAppPOC.security.JwtTokenHelper;
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
    public ResponseEntity<CreateTokenResponse> createToken(@RequestBody JwtAuthRequestDto request){
        try {
            JwtAuthResponseDto jwtAuthResponse = new JwtAuthResponseDto();
            if(request.getOtp().equals(otpService.getCacheOtp(request.getMobileNumber()))){
                this.authenticate(request.getMobileNumber());
                UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getMobileNumber());
                String token = this.jwtTokenHelper.generateToken(userDetails);
                jwtAuthResponse.setToken(token);
                return new ResponseEntity<>(new CreateTokenResponse("success",jwtAuthResponse),HttpStatus.OK);
            }
            else{
                throw new CantCreateToken("Wrong Otp");
            }
        }
        catch (Exception e){
            throw new CantCreateToken(e.getMessage());
        }
    }
    @GetMapping()
    public ResponseEntity<UserDto> userDetails(@RequestBody JwtAuthResponseDto token){
        return new ResponseEntity<>(userService.getByToken(token.getToken()),HttpStatus.OK);
    }
    @PostMapping ( "/requestOtp")
    public ResponseEntity<StatusResponse> getOtp(@RequestBody OtpSendDto otpSendDto){
        String  mobileNumber = otpSendDto.getMobileNumber();
        if(mobileNumber==null){
            throw new MobileNumberValidationException("please enter mobileNumber");
        }
        if(mobileNumber.length()!=13){
            throw new MobileNumberValidationException("please enter a valid 10 digit mobileNumber");
        }
        try{
            otpService.generateOtp(otpSendDto.getMobileNumber());
            return ResponseEntity.ok(new StatusResponse("otp sent successfully",true));

        }catch (Exception e){
            System.out.println(e.getMessage());
            return new ResponseEntity<>(new StatusResponse("something went wrong,please try again later",false),HttpStatus.SERVICE_UNAVAILABLE);
        }
    }
    private void authenticate(String username) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username," ");
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
