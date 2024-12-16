package session.OTP;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;

@Getter
@Setter
public class OTP implements RowMapper<OTP> {
    private String sessionID;
    private  String email;
    private  int otp;

    public OTP(String email, int otp) {
        this.email = email;
        this.otp = otp;
    }
    public OTP() {
    }

    //Map row where map the table result set to the object
    @Override
    public OTP mapRow(ResultSet rs, int rowNum) throws SQLException {
        OTP otp = new OTP();
        otp.setEmail(rs.getString("email"));
        otp.setSessionID(rs.getString("uuid"));
        otp.setOtp(rs.getInt("otp"));
        return otp;
    }
}