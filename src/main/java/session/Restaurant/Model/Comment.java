package session.Restaurant.Model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import session.userInformation.UserInformation;

@Getter
@Setter
@Entity
@Table(name = "comment", schema = "restaurant")
public class Comment {
    @Id
    @Column(name = "id", nullable = false)
    private Integer id;
    @Column(name = "user_id")
    private Integer user_id;
    @Size(max = 255)
    @Column(name = "content")
    private String content;

    @Column(name = "rate")
    private Integer rate;

    @Column(name = "restaurant_id")
    private Integer restaurant;

    @Size(max = 20)
    @Column(name = "day", length = 20)
    private String day;

}