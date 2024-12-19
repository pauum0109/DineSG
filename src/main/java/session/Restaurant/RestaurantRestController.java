package session.Restaurant;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import session.Restaurant.DTO.CommentDTO;
import session.Restaurant.DTO.RestaurantResponseDto;
import session.Restaurant.DTO.RestaurantResponseIndexDto;
import session.Restaurant.DTO.addCategoryDTO;
import session.Restaurant.Model.Comment;
import session.Restaurant.Model.District;

import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/restaurant")
public class RestaurantRestController {
    private final RestaurantService restaurantService;

    public RestaurantRestController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/{id}")
    public Restaurant getRestaurant(@PathVariable int id) {
        return restaurantService.getById(id);
    }

    //Tìm Nhà hàng query theo Category và district
    @PostMapping("/get")
    public List<RestaurantResponseDto> findRestaurant(@RequestParam(value = "district", required = false) String district, @RequestParam(value = "category", required = false) String category) {
        List<Restaurant> data = restaurantService.getRestaurant(district, category);
        return Collections.singletonList(new RestaurantResponseDto(data));
    }

    @GetMapping("/getDistrict")
    public List<District> getD() {
        return restaurantService.getDistrict();
    }

    @PostMapping("/insertCategory")
    public ResponseEntity<Map<String, Object>> insertCategory(@RequestBody addCategoryDTO addCategoryDTO) {
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("message", "Insert category successfully");
            restaurantService.insertRestaurantCategories(addCategoryDTO.getRestaurant_id(), addCategoryDTO.getCategory_id());
            return ResponseEntity.ok().body(response);
        }catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }

    @GetMapping("/getComment")
    public ResponseEntity<List<CommentDTO>> getComment(@RequestParam(value = "restaurant_id") String restaurant_id) {
        return ResponseEntity.ok().body(restaurantService.getCommentByRestaurant(Integer.parseInt(restaurant_id)));
    }
    @PostMapping("/createComment/{user_id}")
    public ResponseEntity<Object> createComment(@RequestBody CommentDTO comment, @PathVariable String user_id) {
        restaurantService.createComment(Integer.parseInt(user_id),comment);
        return ResponseEntity.ok().body(comment);
    }

    @GetMapping("/top3")
    public List<RestaurantResponseIndexDto> findTop3ByRating() {
        List<Restaurant> top3Restaurants = restaurantService.getTop3RestaurantsByRating();

        // Map each Restaurant to RestaurantResponseIndexDto
        return top3Restaurants.stream()
                .map(restaurant -> new RestaurantResponseIndexDto(restaurant)) // Create DTO for each restaurant
                .collect(Collectors.toList()); // Collect the result as a List
    }

    @GetMapping("/all")
    public List<RestaurantResponseIndexDto> findAllRestaurant() {
        List<Restaurant> allRestaurant = restaurantService.getAllRestaurants();

        return allRestaurant.stream()
                .map(restaurant -> new RestaurantResponseIndexDto(restaurant)) // Create DTO for each restaurant
                .collect(Collectors.toList()); // Collect the result as a List
    }

    @GetMapping("/get/category/{categoryId}")
    public ResponseEntity<List<RestaurantResponseIndexDto>> getRestaurantsByCategory(@PathVariable String categoryId) {
        Optional<List<Restaurant>> restaurants = restaurantService.getByCategory(categoryId);
        if (restaurants.isPresent() && !restaurants.get().isEmpty()) {
            List<RestaurantResponseIndexDto> restaurantDtos = restaurants.get().stream()
                    .map(RestaurantResponseIndexDto::new)  // Assuming your RestaurantResponseIndexDto constructor maps the restaurant
                    .collect(Collectors.toList());
            return ResponseEntity.ok().body(restaurantDtos);
        } else {
            return ResponseEntity.noContent().build();
        }
    }

}