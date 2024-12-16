package session.Booking.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import session.utils.Enum.BookingStatus;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@Entity
@Table(name = "booking_decisions", schema = "restaurant")
public class BookingDecision {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "decision_id", nullable = false)
    private long decision_id;
    @Column(name = "admin_user_id")
    private int adminUserId;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDING'")
    @Column(name = "admin_status", nullable = true)
    private BookingStatus status = BookingStatus.POSTPONED;
    @Column(name = "admin_note")
    private String adminNote;
    @NotNull
    @ColumnDefault("CURRENT_TIMESTAMP")
    @Column(name = "decision_date", nullable = false)
    @JsonIgnore
    private Instant Date;
    @Transient
    private String DecisionDate;
    @NotNull
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "booking_id", nullable = false)
    private TableBooking bookingInformation;
    // Getters and setters...

    @PostLoad
    private void formatDecisionDate() {
        if (Date != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-y H:m:s")
                    .withZone(ZoneId.systemDefault());
            this.DecisionDate = formatter.format(Date);
        }
    }

}