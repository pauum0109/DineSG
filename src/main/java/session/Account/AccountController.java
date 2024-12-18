package session.Account;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.boot.jackson.JsonMixinModuleEntries;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
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
    public ResponseEntity<UserInformation> getUserInformation(HttpSession session,@RequestParam int id) {
        return ResponseEntity.ok(userInformationRepo.getUserInformation(id));
    }
    @GetMapping("/login")
    public String login(HttpSession session, Model model, HttpServletResponse response) {

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
///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    @PostMapping("/login")
    public String loginTest(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password, RedirectAttributes redirectAttributes) {
        State<UserDTO> res = accountService.login(username, password);//Server check
        if (res.getStatus() != Status.SUCCESS) {
            redirectAttributes.addFlashAttribute("state", res.getStatus().toString());//Set state
            return "redirect:/account/login";
        }
        //Login thành công se tao session store user id.
        session.setAttribute("user", res.getData().id());
        redirectAttributes.addFlashAttribute("state", res.getStatus().toString());
        return "redirect:/account/login";
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
        return "redirect:/account/createUserInformation/"+res.getData().id();
    }
    @PutMapping("/changePassword")
    public String update(HttpSession session,@RequestHeader String input) {
        try {
            String user = (String) session.getAttribute("user_name");
            Status s = accountService.updatePassword(user, input);
            switch (s) {
                case SUCCESS:
                    return "Password updated successfully";
                case ACCOUNT_NOT_FOUND:
                    return "Account not found";
            }
        } catch (Exception e) {
            return "error";
        }
        return "error";
    }
}