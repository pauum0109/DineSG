package session.Account;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.jackson.JsonMixinModuleEntries;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import session.Account.DTO.PasswordChangeRequest;
import session.Account.DTO.UserDTO;
import session.Account.DTO.createUserDTO;
import session.userInformation.UserInformation;
import session.userInformation.UserInformationRepo;
import session.utils.Enum.Status;
import session.utils.State;

@Controller
@RequestMapping("/account")
public class AccountController {
    private final AccountService accountService;
    private final UserInformationRepo userInformationRepo;
    public AccountController(AccountService accountService, UserInformationRepo userInformationRepo, JsonMixinModuleEntries jsonMixinModuleEntries) {
        this.accountService = accountService;
        this.userInformationRepo = userInformationRepo;
    }
    @GetMapping("/getUserInformation")
    public String getUserInformation(HttpSession session,@RequestParam int id,Model model) {
        Integer user = (Integer) session.getAttribute("user");
        UserInformation userInformation = userInformationRepo.getUserInformation(user);
        model.addAttribute("userInformation",userInformation);
        return "updateInformation" ;
    }
    @GetMapping("/login")
    public String login(HttpSession session, Model model, HttpServletResponse response) {
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate, max-age=0");
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Expires", "0");

        Integer userId = (Integer) session.getAttribute("user");
        String username = (String) session.getAttribute("username");

        if (userId != null && username != null) {
            model.addAttribute("username", username);
            return "redirect:/";
        }

        return "login";
    }


    @GetMapping("/verifyEmail")
    public String verify() {
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
        return "createInformation";
    }
    @GetMapping("/logout")
    public String logout(HttpSession session) {
        session.invalidate(); // Clear the session
        return "redirect:/index"; // Redirect to the homepage or login page
    }

///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/login")
    public String loginTest(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        State<UserDTO> res = accountService.login(username, password);//Server check
        if (res.getStatus() != Status.SUCCESS) {
            redirectAttributes.addFlashAttribute("state", res.getStatus().toString());//Set state
            return "redirect:/account/login";
        }

        session.setAttribute("user", res.getData().id());
        redirectAttributes.addFlashAttribute("state", res.getStatus().toString());
        Integer userID = (Integer) session.getAttribute("user");
        if (accountService.isAdmin(userID)) {
            session.setAttribute("role", 0);
            return "redirect:/admin";
        }
        session.setAttribute("role", 1);
        return "redirect:/index";
    }

    @PostMapping("/register")
    public String register(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, RedirectAttributes redirectAttributes) throws Exception {
        createUserDTO accountDto = new createUserDTO(username, password, email, "USER");
        State<UserDTO> res = accountService.createAccount(accountDto);
        //Server check
        if (res.getStatus() == Status.EXIST_USERNAME) {
            redirectAttributes.addFlashAttribute("state", res.getStatus().toString());//Set state
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/account/register";
        }
        redirectAttributes.addFlashAttribute("state", res.getStatus().toString());
        redirectAttributes.addFlashAttribute("email", email);
        return "redirect:/account/createUserInformation/"+res.getData().id();
    }

    @PostMapping("/validateOldPassword")
    public ResponseEntity<String> validateOldPassword(HttpSession session, @RequestParam("oldPassword") String oldPassword) {
        Integer user = (Integer) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("User not authenticated.");
        }

        boolean isOldPasswordCorrect = accountService.verifyOldPassword(user, oldPassword);
        if (!isOldPasswordCorrect) {
            return ResponseEntity.status(HttpServletResponse.SC_BAD_REQUEST).body("Old password is incorrect.");
        }

        return ResponseEntity.ok("Old password is correct.");
    }

    @PostMapping("/updatePassword")
    public ResponseEntity<String> updatePassword(HttpSession session, @RequestParam("newPassword") String newPassword) {
        Integer user = (Integer) session.getAttribute("user");
        if (user == null) {
            return ResponseEntity.status(HttpServletResponse.SC_UNAUTHORIZED).body("User not authenticated.");
        }

        Status status = accountService.updatePassword2(user, newPassword);
        if (status == Status.SUCCESS) {
            return ResponseEntity.ok("Password updated successfully.");
        } else {
            return ResponseEntity.status(HttpServletResponse.SC_INTERNAL_SERVER_ERROR).body("Password update failed.");
        }
    }

    @GetMapping("/info")
    public String getUserInfo(HttpSession session, Model model) {
        Integer user = (Integer) session.getAttribute("user");
        UserInformation userInformation = userInformationRepo.getUserInformation(user);
        model.addAttribute("userInformation",userInformation);

        if (user == null) {
            return "redirect:/account/login";
        }

        return "person";
    }

    @GetMapping("/recover")
    public String recover() {
        return "recover";
    }

    @GetMapping("/changePassword")
    public String changePassword() {
        return "changePassword";
    }


}