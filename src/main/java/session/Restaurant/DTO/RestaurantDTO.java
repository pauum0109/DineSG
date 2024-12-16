package session.Restaurant.DTO;

import lombok.Getter;
import lombok.Setter;
import session.Restaurant.Restaurant;

import java.util.List;

@Getter
@Setter
public class RestaurantDTO {
    private String restaurant_id= String.valueOf((int) (Math.random() * 9000) + 1000);
    private String name;
    private String address;
    private String district;
    private String picture;
    private String open_time;
    private String close_time;
    private String phone_number;
    private String description;
    private List<String> category;


}