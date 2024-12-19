package session.Restaurant.DTO;

import lombok.Getter;
import lombok.Setter;
import session.Restaurant.Model.Comment;
import session.Restaurant.Restaurant;

import java.util.List;

@Getter
@Setter
public class RestaurantResponseIndexDto {
    private int restaurantId;
    private String name;
    private String address;
    private String district;
    private String picture;
    private String openTime;  // Using camelCase
    private String closeTime; // Using camelCase
    private double rating;

    public RestaurantResponseIndexDto(Restaurant restaurant) {
        this.restaurantId = restaurant.getRestaurant_id();
        this.name = restaurant.getName();
        this.address = restaurant.getAddress();
        this.district = restaurant.getDistrict();
        this.picture = restaurant.getPicture();
        this.openTime = restaurant.getOpen_time();  // Convert to camelCase
        this.closeTime = restaurant.getClose_time(); // Convert to camelCase
        this.rating = restaurant.getRating();
    }
}
