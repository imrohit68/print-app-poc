package com.example.PrintAppPOC.Controller;

import com.example.PrintAppPOC.Dtos.*;
import com.example.PrintAppPOC.Exception.CantCreateToken;
import com.example.PrintAppPOC.Repo.UserRepo;
import com.example.PrintAppPOC.Services.ServiceImpl.OtpService;
import com.example.PrintAppPOC.Services.UserService;
import com.example.PrintAppPOC.security.CustomUserDetailService;
import com.example.PrintAppPOC.security.JwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenHelper jwtTokenHelper;
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;
    private final UserService userService;
    private final UserRepo userRepo;
    @PostMapping("/login")
    public ResponseEntity<CreateTokenResponse> createToken(@RequestBody JwtAuthRequestDto request){
        try {
            if(request.getOtp().equals(otpService.getCacheOtp(request.getMobileNumber()))){
                this.authenticate(request.getMobileNumber());
                UserDetails userDetails = this.customUserDetailService.loadUserByUsername(request.getMobileNumber());
                String token = this.jwtTokenHelper.generateToken(userDetails);
                JwtAuthResponseDto jwtAuthResponse = new JwtAuthResponseDto();
                if (userRepo.existsUsersByMobileNumber(request.getMobileNumber())){
                    jwtAuthResponse.setUserId(request.getMobileNumber());
                }
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
    @PostMapping ( "/requestOtp")
    public ResponseEntity<StatusResponse> getOtp(@RequestBody OtpSendDto otpSendDto){
        try{
            otpService.generateOtp(otpSendDto.getMobileNumber());
            return ResponseEntity.ok(new StatusResponse(true));

        }catch (Exception e){
            return new ResponseEntity<>(new StatusResponse(false),HttpStatus.SERVICE_UNAVAILABLE);
        }
    }

    private void authenticate(String username) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username," ");
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
