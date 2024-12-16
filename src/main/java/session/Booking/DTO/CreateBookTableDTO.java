package session.Booking.DTO;

import lombok.Getter;
import lombok.Setter;
import session.Booking.Model.TableBooking;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class CreateBookTableDTO {
    private int booking_id;//hide this param
    private  int restaurant_id;
    private String name;
    private String phone;
    private String time;
    private int number_of_guests;
    private String note;
    private String email;
    public static TableBooking toEntity(CreateBookTableDTO createBookTableDTO, Integer user_id) {
        TableBooking tableBooking = new TableBooking();
        tableBooking.setBookingId(createBookTableDTO.getBooking_id());
        tableBooking.setUser_id(user_id.toString());
        tableBooking.setRestaurantId(createBookTableDTO.getRestaurant_id());
        tableBooking.setCustomer_name(createBookTableDTO.getName());
        tableBooking.setPhoneNumber(createBookTableDTO.getPhone());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
        LocalDateTime dateTime = LocalDateTime.parse(createBookTableDTO.getTime(), formatter);
        tableBooking.setBookingAt(dateTime.toString());
        tableBooking.setNumOfGuests(createBookTableDTO.getNumber_of_guests());
        tableBooking.setNotes(createBookTableDTO.getNote());
        tableBooking.setEmail(createBookTableDTO.getEmail());
        return tableBooking;
    }


}