package com.example.PrintAppPOC.Services.ServiceImpl;
import com.example.PrintAppPOC.DataTransferObjects.UserDto;
import com.example.PrintAppPOC.Exceptions.ResourceNotFoundException;
import com.example.PrintAppPOC.Exceptions.StoreDoesNotExist;
import com.example.PrintAppPOC.Exceptions.UnknownException;
import com.example.PrintAppPOC.Services.UserService;
import com.twilio.rest.verify.v2.service.Verification;
import com.twilio.rest.verify.v2.service.VerificationCheck;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class OtpService {

    private final UserService userService;
    public String generateOtp(String phoneNo){
        try {
            Verification verification = Verification.creator(
                            "VA657fd4ddc388aff0b5614ba9daaeb2c4",
                            phoneNo,
                            "sms")
                    .create();
            return "OTP Sent Successfully";
        }catch (Exception e) {
            throw new UnknownException(e.getMessage());
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
    public boolean verifyOtp(String mobileNumber,String otp){
        VerificationCheck verificationCheck = VerificationCheck.creator(
                        "VA657fd4ddc388aff0b5614ba9daaeb2c4")
                .setTo(mobileNumber)
                .setCode(otp)
                .create();
        return verificationCheck.getStatus().equals("approved");
    }
}
