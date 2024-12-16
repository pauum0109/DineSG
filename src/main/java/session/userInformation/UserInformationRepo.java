package session.userInformation;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface UserInformationRepo extends JpaRepository<UserInformation, Integer> {
    @Query("SELECT u FROM UserInformation u WHERE u.user_id = :id")
    UserInformation getUserInformation(int id);
    @Query(value = "select * from user_information where user_id in (select user_id from ownrestaurant where restaurant_id = :restaurant_id)",nativeQuery = true)
    UserInformation getUserByRestaurant(int restaurant_id);

}