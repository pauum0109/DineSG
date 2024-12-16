package session.Booking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createDecisionDTO {
    private String booking_id;

    private String status;
    private String note;
}