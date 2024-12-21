package session.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import session.Article.Article;
import session.Article.ArticleRepository;
import session.Booking.BookingService;
import session.Booking.DTO.bookTableDTO;
import session.Booking.DTO.createDecisionDTO;
import session.Booking.Model.BookingDecision;
import session.Category.Category;
import session.Restaurant.DTO.RestaurantDTO;
import session.Restaurant.Model.District;
import session.Restaurant.Restaurant;
import session.Restaurant.RestaurantService;
import session.utils.Service.EmailService.EmailService;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


@Controller
@RequestMapping("/admin")
public class OwnerController {
    private final BookingService bookingService;
    private final RestaurantService restaurantService;
    private final EmailService emailService;
    private final ArticleRepository articleRepository;
    public OwnerController(BookingService bookingService, RestaurantService restaurantService, EmailService emailService, ArticleRepository articleRepository) {
        this.bookingService = bookingService;
        this.restaurantService = restaurantService;
        this.emailService = emailService;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/getBookingOrder")
    public String getAdminBooking(HttpSession session,@RequestParam(required = false) Integer status, Model model, @RequestParam(required = false) Integer restaurant_id) {
        Integer user =(Integer) session.getAttribute("user");
        System.out.println(user);
        List<bookTableDTO> orders = bookingService.getOwnerBooking(user, status,restaurant_id);
        List<Restaurant> restaurants = restaurantService.getOwnerRestaurant(user, restaurant_id);
        List<Category> categories = restaurantService.getCategory();
        Restaurant restaurant =null;
        model.addAttribute("orders", orders);
        model.addAttribute("restaurant_id", restaurant_id);
        model.addAttribute("restaurants", restaurants);
        model.addAttribute("currentStatus", status);
        model.addAttribute("restaurant", restaurant);
        model.addAttribute("categories", categories);


        List<District>  districts = restaurantService.getDistrict();
        if(restaurant_id != null) {
             restaurant = restaurants.stream().filter(r -> Objects.equals(r.getRestaurant_id(), restaurant_id)).findFirst().orElse(null);
             model.addAttribute("districts", districts);

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
            Integer user = (Integer) session.getAttribute("user");
            if(action.equalsIgnoreCase("create")) {
                bookingService.createDecision(user, decisionDTO);
                bookTableDTO book = bookingService.getOwnerBookingDetail(Integer.parseInt(decisionDTO.getBooking_id()));
                emailService.sendResponseEmail(book, decisionDTO);
                return ResponseEntity.ok("Decision created successfully.");
            } else if(action.equalsIgnoreCase("update")) {
                bookingService.updateDecision(Integer.parseInt(decisionDTO.getBooking_id()), decisionDTO.getStatus(), decisionDTO.getNote());
                return ResponseEntity.ok("Decision updated successfully.");
            }
            return ResponseEntity.badRequest().body("Error");
    }
    @Transactional
    @PostMapping(value="/restaurant/{action}",consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> insertRestaurant(HttpSession session, @ModelAttribute() RestaurantDTO RestaurantDTO, @PathVariable String action) {
        Integer owner_id = (Integer) session.getAttribute("user");
        Map<String, Object> response = new LinkedHashMap<>();
        if (action.equalsIgnoreCase("create")) {
            restaurantService.createRestaurant(RestaurantDTO, owner_id);
            return ResponseEntity.ok().body("Create success");
        } else if (action.equalsIgnoreCase("update")) {
            restaurantService.updateRestaurant(RestaurantDTO, owner_id);
            return ResponseEntity.ok("Decision updated successfully.");
        }
        return ResponseEntity.badRequest().body("Error");
    }

    @GetMapping("/createRestaurant")
    public String createRestaurant(Model model){
        List<District> d = restaurantService.getDistrict();
        List<Category> c= restaurantService.getCategory();
        model.addAttribute("districts",d);
        model.addAttribute("categories",c);
        return "createRestaurant";
    }

    @Transactional
    @PostMapping(value = "/restaurant/create")
    public ResponseEntity<Object> createRestaurantRequest(
            HttpSession session,
            @RequestBody(required = false) RestaurantDTO restaurantDTO
    ) {
        Integer owner_id = (Integer) session.getAttribute("user");

        restaurantService.createRestaurant(restaurantDTO, owner_id);
        return ResponseEntity.ok("Create success");
    }

    @GetMapping("/editArticle")
    public String showEditArticlePage(HttpSession session,Model model) {
//        Integer role = (Integer) session.getAttribute("role");
//        if(role != 0) {
//            return "error";
//        }
        List<Article> articles = articleRepository.findAll();
        model.addAttribute("articles", articles);
        return "editArticle";
    }
}