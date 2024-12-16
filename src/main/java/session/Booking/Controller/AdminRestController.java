package session.Booking.Controller;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import session.Booking.BookingService;
import session.Booking.DTO.BookingResponse;
import session.Booking.DTO.bookTableDTO;
import session.Booking.DTO.createDecisionDTO;
import session.Booking.DTO.updateResponseDTO;
import session.Booking.Model.BookingDecision;
import session.Restaurant.DTO.createRestaurantDTO;
import session.Restaurant.Restaurant;
import session.Restaurant.RestaurantService;
import session.utils.Enum.BookingStatus;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class AdminRestController {
    private final BookingService bookingService;
    private final RestaurantService restaurantService;

    public AdminRestController(BookingService bookingService, RestaurantService restaurantService) {
        this.bookingService = bookingService;
        this.restaurantService = restaurantService;
    }



    @GetMapping("/getBookingOrder")
    public String getAdminBooking(@RequestParam(required = false) Integer status, Model model, @RequestParam(required = false) Integer restaurant_id) {
        //Due to native query, we need to pass the status as a string
        List<bookTableDTO> orders = bookingService.getOwnerBooking(6441, status,restaurant_id);
        List<Restaurant> restaurants = restaurantService.getOwnerRestaurant(6441, restaurant_id);
        model.addAttribute("orders", orders);
        model.addAttribute("restaurant_id", restaurant_id);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("currentStatus", status);
        if(restaurant_id != null) {
            Restaurant restaurant = restaurants.stream().filter(r -> Objects.equals(r.getRestaurant_id(), restaurant_id)).findFirst().orElse(null);
            model.addAttribute("restaurant", restaurant);
        }
        return "ownerBookingOrder";
    }

    @GetMapping("/decision")
    public ResponseEntity<Object> getDecision(@RequestParam int decision_id, @RequestParam String action) {
        Map<String, Object> response = new LinkedHashMap<>();

        try {
            if (action.equalsIgnoreCase("view")) {
                BookingDecision decision = bookingService.getDetailDecision(decision_id);
                return ResponseEntity.ok(decision);
            }
            response.put("message", "Invalid action provided. Use 'view', 'create', or 'update'.");
            return ResponseEntity.badRequest().body(response);

        } catch (Exception e) {
            response.put("message", "An error occurred: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @Transactional
    @PostMapping(value="/decision/{action}",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> createDecision(
            HttpSession session,
            @ModelAttribute() createDecisionDTO decisionDTO,
            @PathVariable String action) {
        try {
            if(action.equalsIgnoreCase("create")) {
                bookingService.createDecision(6441, Integer.parseInt(decisionDTO.getBooking_id()), decisionDTO.getNote(), decisionDTO.getStatus());
                return ResponseEntity.ok("Decision created successfully.");
            } else if(action.equalsIgnoreCase("update")) {
                bookingService.updateDecision(Integer.parseInt(decisionDTO.getBooking_id()), decisionDTO.getStatus(), decisionDTO.getNote());
                return ResponseEntity.ok("Decision updated successfully.");
            }
            return ResponseEntity.badRequest().body("Invalid action provided. Use 'view', 'create', or 'update'.");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("An error occurred: " + e.getMessage());
        }
    }


    @PostMapping("/createRestaurant")
    public ResponseEntity<Map<String, Object>> insertRestaurant(HttpSession session, @RequestBody createRestaurantDTO createRestaurantDTO) {
        int owner_id = 8242;
        Map<String, Object> response = new LinkedHashMap<>();
        try {
            response.put("message", "Insert restaurant successfully");
            restaurantService.createRestaurant(createRestaurantDTO,owner_id);
            return ResponseEntity.ok().body(response);
        }catch (Exception e) {
            response.put("message", e.getMessage());
            return ResponseEntity.badRequest().body(response);
        }

    }
}