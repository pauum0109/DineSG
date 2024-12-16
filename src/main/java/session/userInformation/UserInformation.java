package session.userInformation;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import session.Restaurant.Model.Comment;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "user_information", schema = "restaurant")
public class UserInformation {
    @Id
    @Column(name = "user_id", nullable = false)
    @JsonProperty("user_id")

    private Integer user_id;
    @Size(max = 50)
    @NotNull
    @Column(name = "full_name", nullable = false, length = 50)
    private String fullName;

    @Size(max = 15)
    @NotNull
    @Column(name = "phone_number", nullable = false, length = 15)
    private String phoneNumber;

    @Size(max = 255)
    @Column(name = "address")
    private String address;
    @Size(max = 255)
    @Column(name = "profile_picture_url")
    private String profilePictureUrl;
    @Size(max = 255)
    @Column(name = "email")
    private String email;

    @Column(name = "updated_at", nullable = true)
    private Instant updatedAt = Instant.now();
    @OneToMany(fetch = FetchType.LAZY)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JoinColumn(name = "id")
    @JsonIgnore
    private List<Comment> comments;

}