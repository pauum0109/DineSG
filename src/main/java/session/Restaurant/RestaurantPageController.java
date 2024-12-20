package session.Restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import session.Restaurant.DTO.RestaurantResponseIndexDto;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
public class RestaurantPageController {
    private final RestaurantService restaurantService;

    public RestaurantPageController(RestaurantService restaurantService) {
        this.restaurantService = restaurantService;
    }

    @GetMapping("/all-restaurants")
    public String showAllRestaurantsPage() {
        return "all-restaurants";
    }

    @GetMapping("/restaurant/category/{categoryId}")
    public String getRestaurantsByCategoryPage(@PathVariable String categoryId, Model model) {
        Optional<List<Restaurant>> restaurants = restaurantService.getByCategory(categoryId);
        if (restaurants.isPresent() && !restaurants.get().isEmpty()) {
            List<RestaurantResponseIndexDto> restaurantDtos = restaurants.get().stream()
                    .map(RestaurantResponseIndexDto::new)
                    .collect(Collectors.toList());
            model.addAttribute("restaurants", restaurantDtos);
            model.addAttribute("categoryId", categoryId);
            return "restaurantCategoryPage";
        } else {
            return "noRestaurant";
        }
    }

    @GetMapping("/restaurant/show/filter")
    public String getRestaurantsByCategoryAndDistrictPage() {
        return "filteredRestaurantsPage";
    }

    @GetMapping("/admin")
    public String showAdminPage() {
        return "admin";
    }

}
