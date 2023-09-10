package com.example.PrintAppPOC.Controller;

import com.example.PrintAppPOC.Dtos.JwtAuthRequestDto;
import com.example.PrintAppPOC.Dtos.JwtAuthResponseDto;
import com.example.PrintAppPOC.Dtos.OtpSendDto;
import com.example.PrintAppPOC.Repo.UserRepo;
import com.example.PrintAppPOC.Services.ServiceImpl.OtpService;
import com.example.PrintAppPOC.security.CustomUserDetailService;
import com.example.PrintAppPOC.security.JwtTokenHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final JwtTokenHelper jwtTokenHelper;
    private final CustomUserDetailService customUserDetailService;
    private final AuthenticationManager authenticationManager;
    private final OtpService otpService;
    private final UserRepo userRepo;
    @PostMapping("/login")
    public ResponseEntity<JwtAuthResponseDto> createToken(@RequestBody JwtAuthRequestDto request){
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
                return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
            }
            else{
                JwtAuthResponseDto jwtAuthResponse = new JwtAuthResponseDto();
                jwtAuthResponse.setToken("wrong otp");
                return new ResponseEntity<>(jwtAuthResponse,HttpStatus.OK);
            }
        }
        catch (Exception e){
            System.out.println(e);
        }
        return new ResponseEntity<>(new JwtAuthResponseDto(),HttpStatus.OK);
    }
    @GetMapping( "/requestOtp")
    public Map<String,Object> getOtp(@RequestBody OtpSendDto otpSendDto){
        Map<String,Object> returnMap=new HashMap<>();
        try{
            String otp = otpService.generateOtp(otpSendDto.getMobileNumber());
            returnMap.put("otp", otp);
            returnMap.put("status","success");
            returnMap.put("message","Otp sent successfully");
        }catch (Exception e){
            returnMap.put("status","failed");
            returnMap.put("message",e.getMessage());
        }

        return returnMap;
    }

    private void authenticate(String username) {
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(username," ");
        this.authenticationManager.authenticate(usernamePasswordAuthenticationToken);
    }
}
