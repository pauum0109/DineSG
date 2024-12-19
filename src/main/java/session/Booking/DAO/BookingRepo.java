package session.Booking.DAO;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import session.Booking.Model.TableBooking;

import java.util.List;

public interface  BookingRepo extends JpaRepository<TableBooking, Integer> {
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO table_booking (booking_id, restaurant_id, name, phone_number, booking_date, num_of_guests, booking_note, email) " +
            "VALUES (:bid, :restaurant, :customer, :phone, :time, :guests, :note, :email)", nativeQuery = true)
    void createUserBooking(int bid, int restaurant, String customer, String phone, String time, int guests, String note, String email);

    @Query(value = "select  * from table_booking where booking_id in (select booking_id from book where user_id = :id)" +
            "ORDER BY booking_status, updated_at ASC ;", nativeQuery = true)
    List<TableBooking> getListUserBooking(int id);

    @Query(value = "select * from table_booking where booking_id in (select booking_id from book where user_id = :userId )and booking_status = :status", nativeQuery = true)
    List<TableBooking> getUserBookingByStatus(int userId, String status);

    @Query(value="select * from table_booking where restaurant_id in (select restaurant_id from ownrestaurant where user_id = 6441);", nativeQuery = true)
    List<TableBooking> getOwnerBooking(int owner_id);
    @Modifying
    @Query(value = "UPDATE table_booking SET name =:name, phone_number =:phone, booking_date =:date, num_of_guests =:guests, booking_note =:note\n" +
            "WHERE booking_id = :bid", nativeQuery = true)
    void updateUserBooking(int bid, String name, String phone, String date, int guests, String note);

}