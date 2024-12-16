package session.OTP.DAO;

import lombok.Getter;
import lombok.Setter;
import session.OTP.OTP;
@Getter
@Setter
public class createOTP {
    private final String sessionID;
    private final String email;

    public createOTP(String uuid, String email) {
        this.sessionID = uuid;
        this.email = email;
    }

    public static OTP toEnity(String uuid, String email){
        OTP otp = new OTP();
        otp.setSessionID(uuid);
        otp.setEmail(email);
        otp.setOtp((int) (Math.random() * 9000) + 1000);
        return otp;
    }
}