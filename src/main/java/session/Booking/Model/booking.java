package session.Booking.Model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * booking
 */
@Getter
@Setter
public class booking implements RowMapper<booking> {

    private int booking_id;
    private int user_id;
    private int restaurant_id;
    private String booking_date;
    private String num_of_guests;
    private String status;
    private String created_at;
    private String updated_at;

    @Override
    @Nullable
    public booking mapRow(ResultSet arg0, int arg1) throws SQLException {

        booking booking = new booking();
        booking.setBooking_id(arg0.getInt("booking_id"));
        booking.setUser_id(arg0.getInt("user_id"));
        booking.setRestaurant_id(arg0.getInt("restaurant_id"));
        booking.setBooking_date(arg0.getString("booking_date"));
        booking.setNum_of_guests(arg0.getString("num_of_guests"));
        booking.setStatus(arg0.getString("status"));
        booking.setCreated_at(arg0.getString("created_at"));
        booking.setUpdated_at(arg0.getString("updated_at"));
        return booking;
    }


}