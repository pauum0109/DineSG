package session.Restaurant.DAO;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import session.Restaurant.Model.Comment;

import java.util.List;

public interface CommentDAO extends JpaRepository<Comment,Integer> {
    @Query("SELECT c FROM Comment c WHERE c.restaurant = :restaurantId")
    List<Comment> getCommentByRestaurant(@Param("restaurantId") int restaurantId);

}