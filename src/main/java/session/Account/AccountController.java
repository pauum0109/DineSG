package session.Account;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.apache.catalina.User;
import org.springframework.boot.jackson.JsonMixinModuleEntries;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import session.userInformation.UserInformation;
import session.userInformation.UserInformationRepo;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService service;
    private final UserInformationRepo userInformationRepo;


    public AccountController(AccountService service, UserInformationRepo userInformationRepo, JsonMixinModuleEntries jsonMixinModuleEntries) {
        this.service = service;
        this.userInformationRepo = userInformationRepo;

    }
    @GetMapping("/login")
    public String login(HttpSession session, Model model, HttpServletResponse response) {
        // Disable cache tranh tro lai login sau khi dang nhap thanh cong
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");
        if (session.getAttribute("user") != null&&!model.containsAttribute("state")) {
            return "redirect:/index";
        }
        return "form";
    }
    @GetMapping("/verifyEmail")
    public String recover() {
        return "verify";
    }
    @GetMapping("/register")
    public String register(HttpSession session, Model model, HttpServletResponse response) {
        String email = (String) session.getAttribute("email");
        if (email != null) {
            model.addAttribute("email", email);
        }
        return "register";
    }

    @GetMapping("/getUserInformation")
    public UserInformation getUserInformation(@RequestParam int id) {
        return userInformationRepo.getUserInformation(id);
    }
    @PostMapping("/saveUserInformation")
    public ResponseEntity<Object> saveUserInformation(@RequestBody UserInformation userInformation) {
        userInformationRepo.save(userInformation);
        return ResponseEntity.ok(userInformation);
    }
    @PutMapping("/updateUserInformation")
    public ResponseEntity<Object> updateUserInformation(@RequestBody UserInformation userInformation) {
        UserInformation user = userInformationRepo.getUserInformation(userInformation.getUser_id());
        if (user == null) {
            return ResponseEntity.badRequest().body("User not found");
        }
        userInformationRepo.save(userInformation);
        return ResponseEntity.ok(userInformation);
    }
    @GetMapping("/createUserInformation/{id}")
    public String createUserInformation(HttpSession session, @PathVariable String id,Model model){
        model.addAttribute("account_id",id);
        return "information";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session){
        session.removeAttribute("user");
        return "redirect:/index";
    }
}