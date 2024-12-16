package session.OTP;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import session.utils.DAO;

import java.util.List;
import java.util.Optional;
@Component
public class OTPDAO implements DAO<OTP, String> {
    private final JdbcTemplate jdbcTemplate;

    public OTPDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }
    /**
     * Kiểm tra OTP có trong bảng
     */
    //Check query for OTP
    @Override
    public Optional<OTP> findById(String id) {
        try {
            String query = "select * FROM otp WHERE uuid = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new OTP(), id));
        }catch(Exception e){
            System.out.printf("Error: %s", e);
            return Optional.empty();
        }
    }

    public Optional<OTP> isOTPValid(String uuid) {
        try {
            String query = "SELECT * FROM otp\n" + "WHERE created_at >= (NOW() - INTERVAL 10 MINUTE) AND uuid = ?;";
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new OTP(), uuid));
        } catch (Exception e) {
            return Optional.empty();
        }
    }
    @Override
    public Optional<List<OTP>> findAll() {
        return Optional.empty();
    }
    /**
     *
     *  Delete OTP after verify success
     */
    @Override
    public void delete(String uuid) {
        String query = "DELETE FROM OTP WHERE uuid = ?";
        jdbcTemplate.update(query, uuid);
    }
    /**
     *
     *  Update OTP
     */


    @Override
    public void update(OTP entity) {
        String query = "UPDATE OTP SET otp = ? WHERE uuid = ?";
        jdbcTemplate.update(query,entity.getOtp(), entity.getSessionID());
    }

    /**
     *
     *  Create OTP
     */
    @Override
    public void save(OTP entity) {
        String query = "INSERT INTO OTP (uuid,email, otp) VALUES (?,?, ?)";
        jdbcTemplate.update(query,entity.getSessionID(),entity.getEmail(),entity.getOtp());
    }


}