package session.Restaurant.DTO;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import session.Restaurant.Model.Comment;
import session.userInformation.UserInformation;
import session.userInformation.UserInformationRepo;

import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter

public class CommentDTO {
    @JsonIgnore
    private   UserInformationRepo userInformationRepo;
    private int restaurant_id;
    private String comment;
    private int rate;
    private UserInformation user;


    public CommentDTO() {
    }

    public static Comment toEntity(CommentDTO commentDTO,int user_id) {
        Comment comment = new Comment();
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yy");
        String todayDate = formatter.format(new Date());
        comment.setId((int) (Math.random() * 9000) + 1000);
        comment.setRestaurant(commentDTO.getRestaurant_id());
        comment.setUser_id(user_id);
        comment.setRate(commentDTO.getRate());
        comment.setDay(todayDate);
        comment.setContent(commentDTO.getComment());
        return comment;
    }
    public static CommentDTO fromEntity(Comment comment, UserInformation userInformationRepo) {
        CommentDTO commentDTO = new CommentDTO();
        commentDTO.restaurant_id = comment.getRestaurant();
        commentDTO.comment = comment.getContent();
        commentDTO.rate = comment.getRate();
        commentDTO.user = userInformationRepo;
        return commentDTO;
    }

}