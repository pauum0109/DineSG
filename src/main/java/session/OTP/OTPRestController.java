package session.OTP;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import session.Account.DTO.UserDTO;
import session.Account.DTO.createUserDTO;
import session.responseHandler.BodyResponseWithTime;
import session.responseHandler.Exception.ServerException;
import session.utils.Enum.Status;
import session.utils.State;
import session.utils.generateSessionToken;

import java.util.Objects;
@RestController
public class OTPRestController {
    private final OTPService otpService;

    public OTPRestController(OTPService otpService) {
        this.otpService = otpService;
    }

    @PostMapping("/v1/recover")//redirect itself
    public ResponseEntity<Object> recoveryPassword(HttpSession session, @RequestBody createUserDTO accountDto) {
        try {
            //generate session token
            String token = generateSessionToken.get();
            State<UserDTO> res = otpService.sendOTPRecovery(token,accountDto.getEmail());
            if (Objects.requireNonNull(res.getStatus()) == Status.NOT_FOUND) {
                return new ResponseEntity<>(new BodyResponseWithTime<>("Account Not Found. Stay on page", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
            }
            //set token for that session
            session.setAttribute("sessionToken",token);//set token for verify
            session.setAttribute("user_name",res.getData().username());
            session.setMaxInactiveInterval(1800);//Set session time out  minutes
            return new ResponseEntity<>(new BodyResponseWithTime<>("SEND SUCCESS.Move to verify page", HttpStatus.CREATED.value()), HttpStatus.CREATED);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }
    @PostMapping("/v1/verifyOTP")
    public ResponseEntity<Object> verifyOTP(HttpSession session, @RequestHeader int code, @RequestParam String sessionToken) {
        try {
            String token = (String) session.getAttribute("sessionToken");
            if(!sessionToken.equals(token)){
                return new ResponseEntity<>(new BodyResponseWithTime<>("Bad request", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
            }
            State<String> s = otpService.verifyOTP(sessionToken, code);
            switch (s.getStatus()) {
                case ERROR -> {
                    return new ResponseEntity<>(new BodyResponseWithTime<>("Invalid OTP", HttpStatus.BAD_REQUEST.value()), HttpStatus.BAD_REQUEST);
                }
                case SUCCESS -> {
                    session.setAttribute("user_id",s.getData());
                    return new ResponseEntity<>(new BodyResponseWithTime<>("Verify Success", HttpStatus.OK.value()), HttpStatus.OK);
                }
                case NOT_FOUND -> {
                    return new ResponseEntity<>(new BodyResponseWithTime<>("Please resend OTP again", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
                }
                case OUT_DATE -> {
                    return new ResponseEntity<>(new BodyResponseWithTime<>("OutDateOTP. Please resend OTP again", HttpStatus.NOT_FOUND.value()), HttpStatus.NOT_FOUND);
                }
            }
        } catch (Exception e) {

            return new ResponseEntity<>(new BodyResponseWithTime<>(e.getMessage(), HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        }
        return null;
    }
}