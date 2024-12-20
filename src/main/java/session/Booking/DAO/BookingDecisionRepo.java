package session.Booking.DAO;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import session.Booking.Model.BookingDecision;
import session.utils.Enum.BookingStatus;

import java.util.List;


public interface BookingDecisionRepo extends JpaRepository<BookingDecision, Integer> {
    @Query("SELECT b FROM BookingDecision b WHERE b.adminUserId = :id and b.status = :status")
    List<BookingDecision> getBookingOrder(int id, BookingStatus status);

    @Query("SELECT b FROM BookingDecision b WHERE b.adminUserId = :user_id ORDER BY b.status")
    List<BookingDecision> getAllBookingDecision(int user_id);

    @Query("SELECT b FROM BookingDecision b WHERE b.bookingInformation.bookingId = :booking_id")
    BookingDecision getBookingDecisionDetailByBookingId(int booking_id);

    @Query("SELECT b FROM BookingDecision b WHERE b.decision_id = :decision_id")
    BookingDecision getBookingDecisionDetailByDecisionId(int decision_id);

    @Modifying
    @Transactional
    @Query(value = "update booking_decisions set admin_status = :s where booking_id = :id", nativeQuery = true)
    void updateStatus(int id, String s);

    @Modifying
    @Transactional
    @Query("UPDATE BookingDecision  SET adminNote  = :note WHERE bookingInformation.bookingId = :id")
    void updateNotes(int id, String note);

    @Modifying
    @Query(value = "insert into booking_decisions (decision_id,admin_user_id, booking_id, admin_note, admin_status, decision_date) values (:decision_id,:admin_user_id,:bid, :note, :status, CURRENT_TIMESTAMP)", nativeQuery = true)
    void createDecision(int decision_id, int admin_user_id, int bid, String note, String status);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM BookingDecision b WHERE b.decision_id = :id")
    Boolean isDecisionExist(@Param("id") int id);

    @Query("SELECT CASE WHEN COUNT(b) > 0 THEN true ELSE false END FROM BookingDecision b WHERE b.decision_id = :id AND b.status = 'POSTPONED'")
    Boolean isDecisionPending(@Param("id") int id);

    @Query(value = "select * from booking_decisions where booking_id in (select booking_id from book where user_id = ?) AND admin_status != 'POSTPONED'", nativeQuery = true)
    List<BookingDecision> getUserBookingDecision(int userId);
}