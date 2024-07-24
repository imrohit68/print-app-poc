package com.example.PrintAppPOC.Services.ServiceImpl;

import com.example.PrintAppPOC.Configurations.TwilioConfig;
import com.example.PrintAppPOC.DataTransferObjects.UserDto;
import com.example.PrintAppPOC.Exceptions.ResourceNotFoundException;
import com.example.PrintAppPOC.Exceptions.StoreDoesNotExist;
import com.example.PrintAppPOC.Exceptions.UnknownException;
import com.example.PrintAppPOC.Services.UserService;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.DecimalFormat;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class OtpService {
    private static final  Integer EXPIRE_MIN = 5;
    private LoadingCache<String,String> otpCache;
    @Autowired
    private  UserService userService;
    @Autowired
    private TwilioConfig twilioConfig;

    public OtpService() {
        otpCache = CacheBuilder.newBuilder()
                .expireAfterWrite(EXPIRE_MIN, TimeUnit.MINUTES)
                .build(new CacheLoader<>() {
                    @Override
                    public String load(String s) {
                        return "";
                    }
                });
    }

    public String generateOtp(String phoneNo){
        try {
            PhoneNumber to = new PhoneNumber(phoneNo);
            PhoneNumber from = new PhoneNumber(twilioConfig.getTrialNumber());
            String otp = getRandomOTP(phoneNo);
            String otpMessage = "Dear Customer , Your OTP is " + otp + ". Use this otp to log in to Print Application";
            Message.creator(to, from, otpMessage)
                    .create();
            return  otp;
        }catch (Exception e) {
            throw new UnknownException("Something went wrong");
        }
    }
    public void generateOtpStore(String mobileNumber) {
        try{
            UserDto userDto = userService.getById(mobileNumber);
            if(userDto.getStore()==null){
                throw new StoreDoesNotExist();
            }
            generateOtp(mobileNumber);

        }catch (StoreDoesNotExist e){
            throw  e;
        }
        catch (ResourceNotFoundException e){
            throw new StoreDoesNotExist();
        }
        catch (Exception e){
            throw new UnknownException(e.getMessage());
        }
    }

    private String getRandomOTP(String phoneNo) {
        String otp =  new DecimalFormat("0000")
                .format(new Random().nextInt(9999));
        otpCache.put(phoneNo,otp);
        return otp;
    }
    public String getCacheOtp(String key){
        try{
            return otpCache.get(key);
        }catch (Exception e){
            return "";
        }
    }
    //clear stored otp
    public void clearOtp(String key){
        otpCache.invalidate(key);
    }
}
