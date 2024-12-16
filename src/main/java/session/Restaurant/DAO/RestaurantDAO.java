package session.Restaurant.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import session.Restaurant.Model.District;
import session.Restaurant.Restaurant;
import session.responseHandler.Exception.ServerException;

import java.util.List;
import java.util.Optional;

@Component
public class RestaurantDAO {
    private final JdbcTemplate jdbcTemplate;

    public RestaurantDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<Restaurant>> findAll() {
        String query = "select * FROM view_restaurant";
        return Optional.ofNullable(jdbcTemplate.query(query, new Restaurant()));
    }

    public Optional<List<Restaurant>> getByDistrict(String district) {
        try {
            String query = "select * FROM view_restaurant WHERE restaurant_district = ?";
            return Optional.ofNullable(jdbcTemplate.query(query, new Restaurant(), district));
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    public Optional<List<Restaurant>> getByCategory(String category) {
        try {
            String query = "select  view_restaurant.* from view_restaurant\n" + "where view_restaurant.restaurant_id\n" + "in (SELECT restaurant_id FROM restaurant_category where category_id = ?)";
            return Optional.ofNullable(jdbcTemplate.query(query, new Restaurant(), category));
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    public Optional<List<Restaurant>> getByCategoryDistrict(String category, String district) {
        try {
            String query = "select  * from view_restaurant where restaurant_id\n" + "in (SELECT restaurant_id FROM restaurant_category where category_id = ? AND view_restaurant.restaurant_district = ?)";
            return Optional.ofNullable(jdbcTemplate.query(query, new Restaurant(), category, district));
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    public Optional<List<District>> getDistrict() {
        try {
            String query = "select * FROM district";
            return Optional.ofNullable(jdbcTemplate.query(query, new District()));
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    public Optional<Restaurant> findById(int id) {
        try {
            String query = "select view_restaurant.* from view_restaurant\n" + "where view_restaurant.restaurant_id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Restaurant(), id));
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }

    public void insertRestaurant(int owe_id, int res_id, String name, String district, String address, String description,String image, String phoneNumber, String openTime, String closeTime) {
        try {
            String query = "call create_restaurant(? , ? , ? , ? , ? , ? , ? , ? , ?,?)";
            jdbcTemplate.update(query, owe_id, res_id, name, district, address,description, image, phoneNumber, openTime, closeTime);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }
    public void removeRestaurant(int res_id) {
        try {
            String query = "delete from restaurant where restaurant_id = ?";
            jdbcTemplate.update(query, res_id);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }


    public List<Restaurant> getOwnerRestaurant(int ownerId) {
        try {
            String query = "select  * from view_restaurant where restaurant_id in(select restaurant_id from ownrestaurant where user_id = ?);";
            return jdbcTemplate.query(query, new Restaurant(), ownerId);
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }



}