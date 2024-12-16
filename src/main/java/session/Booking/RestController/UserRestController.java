package session.Booking.RestController;

import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import session.Booking.BookingService;
import session.Booking.DTO.BookingResponse;
import session.Booking.DTO.CreateBookTableDTO;
import session.Booking.DTO.bookTableDTO;
import session.Booking.Model.BookingDecision;
import session.userInformation.UserInformation;
import session.userInformation.UserInformationRepo;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
public class UserRestController {


    private final BookingService bookingService;
    public UserRestController(BookingService bookingService) {
        this.bookingService = bookingService;

    }

    @GetMapping("/getBookingResponseDetail")
    public ResponseEntity<Map<String, Object>> getUserOrderResponse(@RequestParam int decision_id) {
        Map<String, Object> response = new LinkedHashMap<>();
        BookingDecision res = bookingService.getDetailDecision(decision_id);
        if(res == null){
            response.put("message", "BookingDecision not been response yet.");
            return ResponseEntity.badRequest().body(response);
        }
        BookingResponse bookingDecision = BookingResponse.fromEntity(res);
        response.put("message", "Retrieved booking adminDecision for decision_id: " + decision_id);
        response.put("data", bookingDecision);
        return ResponseEntity.ok(response);

    }

    @GetMapping("/getUserBooking")
    public ResponseEntity<Object> getUserBooking(@RequestParam int user_id, @RequestParam(required = false) Integer status, @RequestParam(required = false) Integer booking_id) {
        Map<String, Object> response = new LinkedHashMap<>();
        List<bookTableDTO> bookings = bookingService.getUserBooking(user_id, status);
        response.put("data", bookings);
        bookings.stream().filter(booking -> Objects.equals(booking.getBookingId(), booking_id));
        return ResponseEntity.ok(response);
    }

    @PostMapping("/createBookingOrder/{user_id}")
    public CreateBookTableDTO createUserBookingOrder(@RequestBody CreateBookTableDTO book, @PathVariable int user_id) {
        bookingService.createUserBooking(CreateBookTableDTO.toEntity(book,user_id));
        return book;
    }

    @PostMapping("/deleteBooking")
    public void deleteUserBooking(@RequestParam int booking_id) {
        bookingService.deleteUserBooking(booking_id);
    }




//
//    @GetMapping("/getUserBooking")
//    public String getUserBookings(HttpSession session,
//                                  @RequestParam(required = false) Integer status,
//                                  @RequestParam(required = false) Integer booking_id,
//                                  Model model) {
//        Integer user_id = (Integer) session.getAttribute("user_id");
//        if(user_id==null){
//            return "redirect:/login";
//        }
//        List<bookTableDTO> bookings = bookingService.getUserBooking(user_id,status);
//        if (booking_id != null) {
//            bookings = bookings.stream()
//                    .filter(booking -> Objects.equals(booking.getBookingId(), booking_id))
//                    .collect(Collectors.toList());
//        }
//
//        // Add the bookings to the model
//        model.addAttribute("bookings", bookings);
//        // Return the JSP view name
//        return "userBookings";
//    }





}