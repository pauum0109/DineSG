package session.Restaurant;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RestaurantPageController {
    @GetMapping("/all-restaurants")
    public String showAllRestaurantsPage() {
        return "all-restaurants";
    }
}
