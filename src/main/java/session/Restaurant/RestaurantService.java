package session.Restaurant;

import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import session.Category.Category;
import session.Category.CategoryRepo;
import session.Restaurant.DAO.CommentDAO;
import session.Restaurant.DAO.RestaurantDAO;
import session.Restaurant.DTO.CommentDTO;
import session.Restaurant.DTO.RestaurantDTO;
import session.Restaurant.Model.District;
import session.userInformation.UserInformation;
import session.userInformation.UserInformationRepo;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestaurantService {

    private final CategoryRepo categoryDAO;
    private final RestaurantDAO restaurantDAO;
    private final CommentDAO commentDAO;
    private final UserInformationRepo userInformationRepo;


    public RestaurantService(RestaurantDAO restaurantDAO, CategoryRepo category, CommentDAO commentDAO, UserInformationRepo userInformationRepo) {
        this.restaurantDAO = restaurantDAO;
        this.categoryDAO = category;
        this.commentDAO = commentDAO;
        this.userInformationRepo = userInformationRepo;
    }

    //Tìm list các nhà hàng theo tên và địa chỉ
    public List<Restaurant> getRestaurant(String district, String category) {
        List<Restaurant> restaurants;
        if (district == null && category == null) {
            restaurants = restaurantDAO.findAll().orElse(null);
        } else if (district != null && category == null) {
            restaurants = restaurantDAO.getByDistrict(district).orElse(null);
        } else if (district == null) {
            restaurants = restaurantDAO.getByCategory(category).orElse(null);
        } else {
            restaurants = restaurantDAO.getByCategoryDistrict(category, district).orElse(null);
        }
        if (restaurants != null) {
            restaurants.forEach(restaurant -> {
                restaurant.setCategory(categoryDAO.getRestaurantCategory(restaurant.getRestaurant_id()));
            });
        }
        return restaurants;
    }

    public Restaurant getById(int id) {
        Restaurant restaurant = restaurantDAO.findById(id).orElse(null);
        // Check if restaurant is not null before setting the category
        if (restaurant != null) {
            restaurant.setCategory(categoryDAO.getRestaurantCategory(id));
        }
        return restaurant;
    }

    public List<Restaurant> getOwnerRestaurant(int owner_id,Integer restaurant_id) {
        List<Restaurant> restaurants = restaurantDAO.getOwnerRestaurant(owner_id);
        restaurants.forEach(restaurant -> {

            restaurant.setCategory(categoryDAO.getRestaurantCategory(restaurant.getRestaurant_id()));
        });
        return restaurants;
    }


    public List<District> getDistrict() {
        return restaurantDAO.getDistrict().orElse(null);
    }

    //Assume that the list request is fix already
    @Transactional
    public void insertRestaurantCategories(int restaurantId, List<String> categoryIds) {
        for (String categoryId : categoryIds) {
            categoryDAO.insertCategory(restaurantId, categoryId);
        }
    }
    //This function add Category for the restaurant

    @Transactional
    public void createRestaurant(RestaurantDTO restaurant, int owner_id) {
        restaurantDAO.insertRestaurant(owner_id, restaurant.getRestaurant_id(), restaurant.getName(), restaurant.getDistrict(), restaurant.getAddress(), restaurant.getDescription(), restaurant.getPicture(), restaurant.getPhone_number(), restaurant.getOpen_time(),
                restaurant.getClose_time());
        insertRestaurantCategories(Integer.parseInt((restaurant.getRestaurant_id())), restaurant.getCategory());
    }
    @Transactional
    public void updateRestaurant(RestaurantDTO restaurant, int owner_id) {
        restaurantDAO.updateRestaurant(restaurant.getRestaurant_id(), restaurant.getName(), restaurant.getDistrict(), restaurant.getAddress(), restaurant.getDescription(), restaurant.getPicture(), restaurant.getPhone_number(), restaurant.getOpen_time(),
                restaurant.getClose_time());
        categoryDAO.deleteCategory(Integer.parseInt(restaurant.getRestaurant_id()));
        insertRestaurantCategories(Integer.parseInt(restaurant.getRestaurant_id()), restaurant.getCategory());
    }


    public void removeRestaurant(int id) {
        restaurantDAO.removeRestaurant(id);
    }

    public List<CommentDTO> getCommentByRestaurant(int restaurant_id) {
        return commentDAO.getCommentByRestaurant(restaurant_id).stream().map(comment -> {
            UserInformation user = userInformationRepo.findById(comment.getUser_id()).orElse(null);
            return CommentDTO.fromEntity(comment, user);
        }).collect(Collectors.toList());
    }
    @Transactional
    public void createComment(int user_id, CommentDTO comment) {
        commentDAO.save(CommentDTO.toEntity(comment,user_id));
    }


    public List<Category> getCategory() {
        return categoryDAO.findAll();
    }
}