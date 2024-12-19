package session.controllers;

import jakarta.servlet.http.HttpSession;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import session.Booking.BookingService;
import session.Booking.DTO.CreateBookTableDTO;
import session.Booking.DTO.bookTableDTO;
import session.Restaurant.Restaurant;
import session.Restaurant.RestaurantService;
import session.userInformation.UserInformation;
import session.userInformation.UserInformationRepo;
import session.utils.Service.EmailService.EmailService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/user")
public class UserController {
    private final BookingService bookingService;
    private final RestaurantService restaurantService;
    private final UserInformationRepo userInformationRepo;
    private final EmailService emailService;
    public UserController(BookingService bookingService, RestaurantService restaurantService, RestaurantService restaurantService1, UserInformationRepo userInformationRepo, EmailService emailService) {
        this.bookingService = bookingService;
        this.restaurantService = restaurantService1;
        this.userInformationRepo = userInformationRepo;
        this.emailService = emailService;
    }
//    @GetMapping("/getUserBooking")
//    public String getUserBooking(HttpSession session, @RequestParam(required = false) Integer status, Model model) {
//        Integer user_id = (Integer) session.getAttribute("user_id");
//        if (user_id == null) {
//            return "error";
//        }
//        List<bookTableDTO> bookings = bookingService.getUserBooking(user_id, status);
//        model.addAttribute("bookingTable", bookings);
//        return "userBooking";
//    }
//    @PostMapping("/deleteBooking/{booking_id}")
//    public void deleteUserBooking(@PathVariable Integer booking_id) {
//        bookingService.deleteUserBooking(booking_id);
//
//    }

//    @GetMapping("/getDetailBooking/{booking_id}")
//    public String getUserOrderResponse(HttpSession session, Model model, @PathVariable Integer booking_id, @RequestParam String action) {
//        Integer user_id = (Integer) session.getAttribute("user_id");
//        bookTableDTO book = bookingService.getOwnerBookingDetail(booking_id);
//        if(book==null||user_id == null){
//            return "error";
//        }
//        if(Objects.equals(action, "edit")&& Objects.equals(book.getStatus(), "PENDING")){
//            model.addAttribute("bookingDecision", book);
//            return "bookDetailUpdate";//Page này cho phép update
//        }
//        else if(Objects.equals(action, "view")){
//            model.addAttribute("bookingDecision", book);
//            return "bookDetail";//Page này view kèm response if cos
//        }
//        return "error";
//    }


    @GetMapping("/booking/{restaurant_id}")
    public String createOrder(HttpSession session,@PathVariable String restaurant_id, Model model, @RequestParam(required = true) Integer booking_id) {
        try {
            Integer user_id = (Integer) session.getAttribute("user");
            Integer bid = (Integer) session.getAttribute("booking_id");
            if(user_id == null || bid == null || !bid.equals(booking_id)){
                return "error";
            }
            Restaurant restaurant = restaurantService.getById(Integer.parseInt(restaurant_id));
            UserInformation user = userInformationRepo.getUserInformation(user_id);
            model.addAttribute("booking_id", booking_id);//param hide
            model.addAttribute("user", user);
            model.addAttribute("restaurant", restaurant);
            return "booking";
        } catch (Exception e) {
            return "error";
        }
    }


    @GetMapping("/getUserBooking")
    public String getUserBooking(HttpSession session, @RequestParam(required = false) Integer status, Model model) {
        UserInformation user = userInformationRepo.getUserInformation(8242);
        List<bookTableDTO> bookings = bookingService.getUserBooking(8242, status);
        model.addAttribute("currentStatus", status);
        model.addAttribute("bookingTable", bookings);
        model.addAttribute("user", user);
        return "userBooking";
    }
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/booking/{restaurant_id}")
    public ResponseEntity<Object> getBookingId(HttpSession session, @PathVariable String restaurant_id, Model model) {
        Integer user_id = (Integer) session.getAttribute("user");
        if (user_id == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        Integer booking_id = (int) (Math.random() * 9000) + 1000;
        session.setAttribute("booking_id", booking_id);
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("booking_id", booking_id);
        return ResponseEntity.ok(response);
    }

    @Transactional
    @PostMapping(value = "/updateBooking", consumes = MediaType.APPLICATION_FORM_URLENCODED_VALUE)
    public ResponseEntity<Object> updateBooking(@ModelAttribute CreateBookTableDTO book) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(book.getTime(), formatter);

        bookingService.updateUserBooking(book.getBooking_id(), book.getName(), book.getPhone(), dateTime.toString(), book.getNumber_of_guests(), book.getNote()
                );

        return ResponseEntity.ok("Success") ;
    }
    @Transactional
    @PostMapping(value = "/createOrder", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Object> createUserBookingOrder(HttpSession session, @RequestBody CreateBookTableDTO book) {
        try {
            Integer user_id = (Integer) session.getAttribute("user");
            bookingService.createUserBooking(CreateBookTableDTO.toEntity(book, user_id));
            Restaurant restaurant = restaurantService.getById(book.getRestaurant_id());
            emailService.sendConfirm(restaurant,book);
            emailService.sendAnnounce(book);
            return ResponseEntity.ok("Booking created successfully");
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.badRequest().body("Booking creation failed");
        }
    }

    @DeleteMapping("/deleteBooking/{booking_id}")
    public ResponseEntity<Object> deleteUserBooking(@PathVariable Integer booking_id) {
        try{
            bookingService.deleteUserBooking(booking_id);;
            return ResponseEntity.ok("Booking deleted successfully");

        }catch (Exception e){
            return ResponseEntity.badRequest().body("Booking deletion failed");
        }
    }}