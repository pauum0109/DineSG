package session.OTP;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import session.Account.DTO.UserDTO;

import session.Account.AccountService;
import session.responseHandler.Exception.ServerException;
import session.utils.State;
import session.utils.Enum.Status;
import session.utils.generateSessionToken;

import java.util.Objects;

@Controller
public class OTPAPI {
    private final OTPService otpService;
    private final AccountService accountService;

    public OTPAPI(OTPService otpService, AccountService accountService) {
        this.otpService = otpService;
        this.accountService = accountService;
    }


    @PostMapping(value = "/verifyEmail")
    public String verifyEmail(HttpSession session, @RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            if (accountService.isEmailExist(email)) {
                redirectAttributes.addFlashAttribute("state", "error");//Set state
                return "redirect:/verifyEmail";
            }
            //generate session token
            String token = generateSessionToken.get();
            //set token for that session;
            otpService.sendOTPVerify(token, email);
            session.setAttribute("sessionToken", token);//set token for verify
            session.setMaxInactiveInterval(1800);//Set session time out  minutes
            redirectAttributes.addFlashAttribute("email", email);
            session.setAttribute("email", email);
            redirectAttributes.addFlashAttribute("isRegister", true);
            return "redirect:/verifyOTP/register/" + token;
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }
    @PostMapping("/recover")//redirect itself
    public String recoveryPassword(HttpSession session, @RequestParam String email, RedirectAttributes redirectAttributes) {
        try {
            //generate session token
            String token = generateSessionToken.get();
            State<UserDTO> res = otpService.sendOTPRecovery(token, email);
            if (Objects.requireNonNull(res.getStatus()) != Status.SUCCESS) {
                redirectAttributes.addFlashAttribute("state", res.getStatus().toString());//Set state
                return "redirect:/recover";
            }
            //set token for that session
            session.setAttribute("sessionToken", token);//set token for verify
            session.setMaxInactiveInterval(1800);//Set session time out  minutes
            redirectAttributes.addFlashAttribute("email", email);
            session.setAttribute("email", email);
            session.setAttribute("user_name", res.getData().username());
            return "redirect:/verifyOTP/recover/" + token;
        } catch (Exception e) {
            throw new ServerException(e.getMessage());
        }
    }
        @PostMapping("/verifyOTP/{actionType}/{sessionToken}")
        public String verifyOTP(HttpSession session, @RequestParam int otp, @PathVariable String sessionToken, RedirectAttributes redirectAttributes,
                                Model model, @PathVariable String actionType) {
            String token = (String) session.getAttribute("sessionToken");
            String message = " ";
            if (!sessionToken.equals(token)) {
                return "error";
            }
            State<String> s = otpService.verifyOTP(sessionToken, otp);
            switch (s.getStatus()) {
                case ERROR -> {
                    message = "Error OTP";
                }
                case SUCCESS -> {
                    session.setAttribute("isVerify", true);
                    if ("register".equalsIgnoreCase(actionType)) {
                        redirectAttributes.addFlashAttribute("email", s.getData());
                        return "redirect:/register";
                    } else if ("recover".equalsIgnoreCase(actionType)) {
                        return "redirect:/resetPassword/" + token;
                    }
                }
                case NOT_FOUND -> {
                    message = "Not found";
                }
                case OUT_DATE -> {
                    message = "OTP Out date";
                }
            }
        redirectAttributes.addFlashAttribute("message", message);
        redirectAttributes.addFlashAttribute("sessionToken", sessionToken);
        redirectAttributes.addFlashAttribute("email", s.getData());
        return "redirect:/verifyOTP/{actionType}/" + token;
    }
    @PostMapping("/resetPassword/{sessionToken}")
    public String reset(HttpSession session, @RequestParam String password, @PathVariable String sessionToken) {
        String token = (String) session.getAttribute("sessionToken");
        Boolean isVerify = (Boolean) session.getAttribute("isVerify");
        if (!sessionToken.equals(token) || !isVerify) {
            return "error";
        }
        String user = (String) session.getAttribute("user_name");
        accountService.updatePassword(user, password);
        session.invalidate();
        return "redirect:/account/login";
    }
}