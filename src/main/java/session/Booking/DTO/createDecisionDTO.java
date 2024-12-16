package session.Booking.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createDecisionDTO {
    private String booking_id;
    private int decision_id = (int) (Math.random() * 9000) + 1000;
    private String status;
    private String note;
}