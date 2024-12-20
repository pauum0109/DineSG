package session.controllers;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import session.Account.DTO.UserDTO;
import session.Restaurant.Restaurant;
import session.Restaurant.Model.District;

import session.Account.AccountService;
import session.Restaurant.RestaurantService;

import java.util.List;


@Controller
public class    HomeController {
    private final AccountService service;
    private final RestaurantService restaurantService;

    public HomeController(AccountService service, RestaurantService restaurantService) {
        this.service = service;
        this.restaurantService = restaurantService;
    }

    @RequestMapping("/get/{id}")
    public String home(@PathVariable int id, Model model) {
        try {
            //View All account base on UserDTO format
            UserDTO res = service.findUser(id);
            System.out.println("id is" + res.id());
            model.addAttribute("user", res);
            return "person";  // user.html will be returned
        } catch (Exception e) {
            return "error";
        }
    }






    //    @PostMapping(value = "/submit")


//    @RequestMapping(value = "/forgotpass/send", method = RequestMethod.POST)
//    public String passRecovery(@ModelAttribute("formData") createAccountDTO formData, Model model) throws Exception {
//        // Process formData (which contains username, email, password)
//        try {
//            State<UserDTO> res = service.createAccount(formData);
//            switch (res.getStatus()) {
//                case SUCCESS:
//                    model.addAttribute("message", "Account created successfully");
//
//                    //return new page
//                    break;
//                case EXIST_USERNAME:
//                    model.addAttribute("message", "Account already exists");
//                    break;
//                case EXIST_EMAIL:
//                    model.addAttribute("message", "Email already exists");
//                    break;
//            }
//            return "register";
//        } catch (Exception e) {
//            throw new ServerException(e.getMessage());
//        }
//
//    }

    //////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @RequestMapping("/blog")
    public String blog() {
        return "blog";
    }

    @RequestMapping("/index")
    public String index(HttpSession session, Model model, @RequestParam(required = false) String district,@RequestParam(required = false) String category) {
        String username = (String) session.getAttribute("username");
        if (username != null) {
            model.addAttribute("username", username); // Add username to model for rendering
        }
        List<Restaurant> restaurantList = restaurantService.getRestaurant(district , null);
        List<District> districtList = restaurantService.getDistrict();
        model.addAttribute("restaurantList", restaurantList);
        model.addAttribute("districtList", districtList);
        model.addAttribute("district", district);
        model.addAttribute("category", category);
        System.out.printf("district is %s", districtList.get(0));
        UserDTO res = null;
        try {
            int id = (int) session.getAttribute("user");
            res = service.findUser(id);
        } catch (Exception ignored) {
        }
        model.addAttribute("user", res);

        return "index";
    }

//
//    @RequestMapping("/restaurant/get")
//    public String restaurant(HttpSession session, Model model, @RequestParam("category") String category) {
//        List<Restaurant> list_restaurant = restaurantService.getRestaurant(null,category);
//        model.addAttribute("list_restaurant",list_restaurant);
//        model.addAttribute("category", category);
//        return "category_restaurants";
//    }
//
//    @RequestMapping("/contact")
//    public String contact() {
//        return "contact";
//    }
//
//    @RequestMapping("/service")
//    public String service() {
//        return "service";
//    }
//
//    @RequestMapping("/shop")
//    public String shop() {
//        return "shop";
//    }


}