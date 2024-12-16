package session.Booking.DTO;

import lombok.Getter;
import lombok.Setter;
import session.Booking.Model.BookingDecision;
import session.utils.Enum.BookingStatus;
import session.utils.timeFormat;

@Getter
@Setter
public class BookingResponse {
    private long decisionId;
    private int bookingId;
    private BookingStatus status;
    private String adminNote;
    private String decisionDate;


    public static BookingResponse fromEntity(BookingDecision booking){
        BookingResponse responseDTO = new BookingResponse();
        responseDTO.decisionId = booking.getDecision_id();
        responseDTO.bookingId = booking.getBookingInformation().getBookingId();
        responseDTO.status = booking.getStatus();
        responseDTO.adminNote = booking.getAdminNote();
        responseDTO.decisionDate = timeFormat.format(booking.getDate());

        return responseDTO;
    }
}