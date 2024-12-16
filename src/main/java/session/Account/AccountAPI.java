package session.Account;


import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import session.OTP.OTPService;
import session.Account.DTO.UserDTO;
import session.Account.DTO.createUserDTO;
import session.utils.State;
import session.utils.Enum.Status;

@Controller
public class AccountAPI {
    private final AccountService accountService;
    public AccountAPI(AccountService service, OTPService otpService) {
        this.accountService = service;
    }
    @PostMapping("/account/login")
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
    public String register(HttpSession session, @RequestParam("username") String username, @RequestParam("password") String password, @RequestParam("email") String email, @RequestParam("role") String role, RedirectAttributes redirectAttributes) throws Exception {
        createUserDTO accountDto = new createUserDTO(username, password, email, role);
        State<UserDTO> res = accountService.createAccount(accountDto);
        //Server check
        if (res.getStatus() == Status.EXIST_USERNAME) {
            redirectAttributes.addFlashAttribute("state", res.getStatus().toString());//Set state
            redirectAttributes.addFlashAttribute("username", username);
            redirectAttributes.addFlashAttribute("email", email);
            return "redirect:/register";
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