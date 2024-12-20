package session.Restaurant.DAO;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;
import session.Restaurant.Model.District;
import session.Restaurant.Restaurant;

import java.rmi.ServerException;
import java.util.List;
import java.util.Optional;

@Component
public class RestaurantDAO {
    private final JdbcTemplate jdbcTemplate;

    public RestaurantDAO(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<List<Restaurant>> findAll() {
        String query = "select * FROM restaurant";
        return Optional.ofNullable(jdbcTemplate.query(query, new Restaurant()));
    }

    public Optional<List<Restaurant>> getByDistrict(String district)  {
        try {
            String query = "select * FROM restaurant WHERE restaurant_district = ?";
            return Optional.ofNullable(jdbcTemplate.query(query, new Restaurant(), district));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<Restaurant>> getByCategory(String categoryId) {
        try {
            String query = "SELECT r.* FROM restaurant r " +
                    "JOIN restaurant_category rc ON r.restaurant_id = rc.restaurant_id " +
                    "WHERE rc.category_id = ?";
            List<Restaurant> restaurants = jdbcTemplate.query(query, new Object[]{categoryId}, new Restaurant());
            return Optional.ofNullable(restaurants);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


    public Optional<List<Restaurant>> getByCategoryDistrict(String category, String district) {
        try {
            String query = "select  * from restaurant where restaurant_id\n" + "in (SELECT restaurant_id FROM restaurant_category where category_id = ? AND restaurant.restaurant_district = ?)";
            return Optional.ofNullable(jdbcTemplate.query(query, new Restaurant(), category, district));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<List<District>> getDistrict() {
        try {
            String query = "select * FROM district";
            return Optional.ofNullable(jdbcTemplate.query(query, new District()));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public Optional<Restaurant> findById(int id)  {
        try {
            String query = "select restaurant.* from restaurant\n" + "where restaurant.restaurant_id = ?";
            return Optional.ofNullable(jdbcTemplate.queryForObject(query, new Restaurant(), id));
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public void insertRestaurant(String res_id, String name, String district, String address, String description,String image, String phoneNumber, String openTime, String closeTime) {
        try {
            String query = "insert into restaurant (restaurant_id, restaurant_name, restaurant_district, restaurant_address, restaurant_image, phone_number, open_time, close_time, restaurant_description) VALUES (?,?,?,?,?,?,?,?,?)";
            jdbcTemplate.update(query, res_id, name, district,address, image, phoneNumber, openTime, closeTime, description);
        } catch (Exception e) {
            return;
        }
    }

    public void insertOwnRestaurant(int owerID, int resID) {
        try {
           String query = "insert into ownrestaurant (user_id, restaurant_id) VALUES (?, ?)";
           jdbcTemplate.update(query, owerID, resID);
        } catch(Exception e) {
            return;
        }
    }

    public void removeRestaurant(int res_id)  {
        try {
            String query = "delete from restaurant where restaurant_id = ?";
            jdbcTemplate.update(query, res_id);
        } catch (Exception e) {
            return;
        }
    }

    public List<Restaurant> getOwnerRestaurant(int ownerId)  {
        try {
            String query = "select  * from restaurant where restaurant_id in(select restaurant_id from ownrestaurant where user_id = ?);";
            return jdbcTemplate.query(query, new Restaurant(), ownerId);
        } catch (Exception e) {
            return null;
        }
    }

    public void updateRestaurant(String restaurantId, String name, String district, String address, String description, String picture, String phoneNumber, String openTime, String closeTime) {
        try {
            String query = "UPDATE restaurant\n" +
                    "SET\n" +
                    "    restaurant_name = ?,\n" +
                    "    restaurant_district = ?,\n" +
                    "    restaurant_address = ?,\n" +
                    "    restaurant_image = ?,\n" +
                    "    phone_number = ?,\n" +
                    "    open_time = ?,\n" +
                    "    close_time = ?,\n" +
                    "    restaurant_description = ?\n" +
                    "WHERE\n" +
                    "    restaurant_id = ?;";
            jdbcTemplate.update(query, name, district, address, picture, phoneNumber, openTime, closeTime, description, restaurantId);
        } catch (Exception e) {
            return ;
        }
    }

    public Optional<List<Restaurant>> findTop3ByRating() {
        try {
            String query = "SELECT * FROM restaurant ORDER BY rating DESC LIMIT 3";
            List<Restaurant> top3Restaurants = jdbcTemplate.query(query, new Restaurant());
            return Optional.ofNullable(top3Restaurants);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }


}