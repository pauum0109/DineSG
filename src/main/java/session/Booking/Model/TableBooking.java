package session.Booking.Model;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import session.utils.Enum.BookingStatus;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Getter
@Setter
@Table(name = "table_booking")
public class TableBooking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "booking_id")
    private Integer bookingId;
    @Column(name = "restaurant_id", nullable = false,insertable=false, updatable=false)
    private Integer restaurantId;
    @Column(name = "name", length = 50, nullable = false)
    private String customer_name;
    @Column(name = "phone_number", length = 15, nullable = false)
    private String phoneNumber;
    @Column(name = "booking_date", nullable = false)
    @JsonIgnore
    private LocalDateTime booking;
    @Column(name = "num_of_guests")
    private Integer numOfGuests;
    @Column(name = "email", length = 50, nullable = false)
    private String email;
    @Enumerated(EnumType.STRING)
    @ColumnDefault("'PENDING'")
    @Column(name = "booking_status", nullable = true)
    private BookingStatus status = BookingStatus.PENDING;
    @Column(name = "booking_note")
    private String notes;
    @Column(name = "updated_at", columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP")
    @JsonIgnore
    private LocalDateTime update;
    @Transient // This field is not persisted in the database
    private String UpdatedAt;
    @Transient // This field is not persisted in the database
    private String BookingAt;
    @Transient // This field is not persisted in the database
    private Integer user_id;
    @NotNull
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;
    @PostLoad
    private void formatUpdatedAt() {
        if (update != null||BookingAt!=null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("d-M-y H:m:s");
            this.UpdatedAt = update.format(formatter);
            this.BookingAt = booking.format(formatter);
        }
    }

}