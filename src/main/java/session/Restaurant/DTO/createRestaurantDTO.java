package session.Restaurant.DTO;

import lombok.Getter;
import lombok.Setter;
import session.Restaurant.Restaurant;

import java.util.List;

@Getter
@Setter
public class createRestaurantDTO {

    private String name;
    private String address;
    private String district;
    private String picture;
    private String open_time;
    private String close_time;
    private String phone_number;
    private String description;
    private List<String> category;

    public Restaurant toEntity() {
        Restaurant res = new Restaurant();
        res.setRestaurant_id((int) (Math.random() * 9000) + 1000);
        res.setName(getName());
        res.setAddress(getAddress());
        res.setDistrict(getDistrict());
        res.setPicture(getPicture());
        res.setOpen_time(getOpen_time());
        res.setClose_time(getClose_time());
        res.setDescription(getDescription());
        res.setPhone_number(getPhone_number());
        return res;
    }
}