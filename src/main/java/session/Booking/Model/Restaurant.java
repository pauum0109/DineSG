package session.Booking.Model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "restaurant", schema = "restaurant")
public class Restaurant {
    @Id
    @Column(name = "restaurant_id", nullable = false)
    private Integer id;

    @Size(max = 100)
    @Column(name = "restaurant_name", length = 100)
    private String restaurantName;

}