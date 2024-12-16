package session.Booking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class updateResponseDTO {
    private String booking_id;
    private String note;
    private String status;
}