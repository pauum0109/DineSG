package session.Booking;

import org.springframework.stereotype.Service;
import session.Booking.DAO.BookingDecisionRepo;
import session.Booking.DAO.BookingRepo;
import session.Booking.DTO.BookingResponse;
import session.Booking.DTO.bookTableDTO;
import session.Booking.DTO.createDecisionDTO;
import session.Booking.Model.BookingDecision;
import session.Booking.Model.TableBooking;
import session.utils.Enum.BookingStatus;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class BookingService {
    private final BookingRepo bookingRepo;
    private final BookingDecisionRepo bookingDecisionRepo;
    private List<bookTableDTO> data;

    public BookingService(BookingRepo bookingRepo, BookingDecisionRepo bookingDecisionRepo) {
        this.bookingRepo = bookingRepo;
        this.bookingDecisionRepo = bookingDecisionRepo;
    }

    /**
     * Lay thong tin nhung adminDecision cua Admin da duyet
     */
    public List<BookingResponse> getAdminDecision(int owner_id, BookingStatus status) {
        List<BookingDecision> bookingDecisions = null;
        if (status == null) {
            bookingDecisions = bookingDecisionRepo.getAllBookingDecision(owner_id);
        } else {
            bookingDecisions = bookingDecisionRepo.getBookingOrder(owner_id, status);
        }
        return bookingDecisions.stream().map(BookingResponse::fromEntity).collect(Collectors.toList());
    }


    public bookTableDTO getOwnerBookingDetail(int booking_id) {
        TableBooking booking = bookingRepo.findById(booking_id).orElse(null);
        if (booking == null) {
            return null;
        }
        BookingDecision decisions = bookingDecisionRepo.getBookingDecisionDetailByBookingId(booking_id);
        return bookTableDTO.fromEntity(booking, decisions);
    }


    public BookingDecision getDetailDecision(int decision_id) {
        if (bookingDecisionRepo.isDecisionPending(decision_id)) {
            return null;
        }
        return bookingDecisionRepo.getBookingDecisionDetailByDecisionId(decision_id);
    }

    /**
     * Admin duyet thong tin
     */
    public void updateDecision(int booking_id, String status, String note) {
        if (status != null) {
            bookingDecisionRepo.updateStatus(booking_id, status);
        }
        // Process note update if note is provided
        if (note != null) {
            bookingDecisionRepo.updateNotes(booking_id, note);
        }
    }

    /**
     * Kiem tra admin da dua ra decesion hay chua
     */
    public boolean isDecisionExist(int booking_id) {
        return bookingDecisionRepo.isDecisionExist(booking_id);
    }

    /**
     * Kiem tra admin da phan hoi booking chua
     */
    public boolean isDecisionPending(int booking_id) {
        return bookingDecisionRepo.isDecisionPending(booking_id);
    }

    /**
     * Admin dua ra decison cho booking
     */
    public void createDecision(int admin_user_id, createDecisionDTO decision) {
        bookingDecisionRepo.createDecision(decision.getDecision_id(), admin_user_id, Integer.parseInt(decision.getBooking_id()), decision.getNote(), decision.getStatus());
        bookingDecisionRepo.updateStatus(Integer.parseInt(decision.getBooking_id()), decision.getStatus());
    }

    /**
     * Update thong tin Booking
     */
    public void updateUserBooking(int bid, String name, String phone, String date, int guests, String note) {
        bookingRepo.updateUserBooking(bid, name, phone, date, guests, note);
    }

    /**
     * Xoa user Booking
     */

    public void deleteUserBooking(int booking_id) {
        bookingRepo.deleteById(booking_id);
    }

    /**
     * Account Generate Booking
     */
    public void createUserBooking(TableBooking booking) {
        bookingRepo.createUserBooking(booking.getBookingId(), booking.getUser_id(), booking.getRestaurantId(), booking.getCustomer_name(), booking.getPhoneNumber(), booking.getBookingAt(), booking.getNumOfGuests(), booking.getNotes(),booking.getEmail());
    }
    /**
     * Found owner booking base on owner_id, bookingStatus
     */
    public List<bookTableDTO> getOwnerBooking(int owner_id, Integer bookingStatus, Integer restaurant_id) {
        BookingStatus status = (bookingStatus != null) ? BookingStatus.values()[bookingStatus] : null;
        List<TableBooking> bookings = bookingRepo.getOwnerBooking(owner_id);
        List<BookingDecision> decisions = bookingDecisionRepo.getAllBookingDecision(owner_id);
        List<bookTableDTO> data = bookTableDTO.fromEntity(bookings, decisions);
        if (restaurant_id != null) {
            data = data.stream()
                    .filter(booking -> Objects.equals(booking.getRestaurant_id(), restaurant_id))
                    .collect(Collectors.toList());
        }
        if (status != null) {
            data = data.stream()
                    .filter(booking -> Objects.equals(booking.getStatus(), status.toString()))
                    .collect(Collectors.toList());
        }
        return data;
    }


    /**
     * Lay thong tin booking cua user
     */
    public List<bookTableDTO> getUserBooking(int user_id, Integer bookingStatus) {
        BookingStatus  status = null;
        if(bookingStatus != null){
            status = BookingStatus.values()[bookingStatus];
        }
        List<TableBooking> bookings = bookingRepo.getListUserBooking(user_id);
        List<BookingDecision> decisions = bookingDecisionRepo.getUserBookingDecision(user_id);

        data = bookTableDTO.fromEntity(bookings, decisions);
        if (status == null) {
            return data;
        } else {
            data = data.stream().filter(booking -> Objects.equals(booking.getStatus(), BookingStatus.values()[bookingStatus].toString())).collect(Collectors.toList());
        }
        return data;
    }

}