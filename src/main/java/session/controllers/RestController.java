package session.controllers;

//error redirect itself

import jakarta.servlet.http.HttpSession;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import session.Booking.DAO.BookingRepo;
import session.Account.DTO.UserDTO;
import session.Account.DTO.createUserDTO;

import session.OTP.OTPService;
import session.responseHandler.BodyResponseWithTime;
import session.responseHandler.Exception.ServerException;
import session.Account.AccountService;
import session.utils.State;

/**
 * RestController
 */

@org.springframework.web.bind.annotation.RestController

public class RestController {
    private final AccountService service;
    private final OTPService otpService;


    public RestController(AccountService service, OTPService otpService, BookingRepo bookingRepo) {
        this.service = service;
        this.otpService = otpService;

    }
    //admin view all account

    @PostMapping("/get")
    public ResponseEntity<Object> getAll() {
        try {
            //View All account base on UserDTO format
            return new ResponseEntity<>(service.getAllAccount(), new HttpHeaders(), HttpStatus.OK);
        } catch (Exception e) {
            throw new ServerException(e.getMessage(), new HttpHeaders());
        }
    }
    //Để homecontroller
    @PostMapping("/booking")
    public String postLogin() {
        return "login";
    }

    //
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        try {
            int user = (int) session.getAttribute("user");
            UserDTO userDto = service.findUser(user);
            String annouce = "Log out " + userDto.username();
            session.invalidate();
            return "index";  // Redirect to login page
        } catch (Exception e) {
            return "Please login to continue";
        }
    }
    @RequestMapping("account/getInformation")
    public ResponseEntity<Object> findUserById(HttpSession session) {
        try {
            int id = (int) session.getAttribute("user");
            UserDTO res = service.findUser(id);
            return new ResponseEntity<>(new BodyResponseWithTime<>(res, HttpStatus.OK.value()), HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(new BodyResponseWithTime<>("Please login to continue", HttpStatus.UNAUTHORIZED.value()), HttpStatus.UNAUTHORIZED);
        }
    }
    //Create Account
    @PostMapping("account/register")
    public ResponseEntity<Object> create(@RequestBody createUserDTO accountDto) {
        HttpHeaders headers = new HttpHeaders();
        try {
            State<UserDTO> res = service.createAccount(accountDto);
            switch (res.getStatus()) {
                case EXIST_USERNAME -> {
                    return new ResponseEntity<>(new BodyResponseWithTime<>(res.getData().username() + " already exists", HttpStatus.BAD_GATEWAY.value()), headers, HttpStatus.BAD_GATEWAY);
                }
                case EXIST_EMAIL -> {
                    return new ResponseEntity<>(new BodyResponseWithTime<>(res.getData().email() + " exist", HttpStatus.BAD_GATEWAY.value()), headers, HttpStatus.BAD_GATEWAY);
                }
            }
        } catch (Exception e) {
            throw new ServerException(e.getMessage(), headers);
        }
        return new ResponseEntity<>(new BodyResponseWithTime<>("Create Success", HttpStatus.CREATED.value()), headers, HttpStatus.CREATED);
    }


    //Send OTP
}
//if(http!=null){return new ResponseEntity<>(new BodyResponseWithTime<>("You have been login", HttpStatus.CREATED.value()), HttpStatus.CREATED);}