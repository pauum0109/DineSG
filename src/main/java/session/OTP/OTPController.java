package session.OTP;

import jakarta.servlet.http.HttpSession;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
@Controller
public class OTPController {

  private final OTPService otpService;

  public OTPController(OTPService otpService) {
    this.otpService = otpService;
  }

  @GetMapping("/verifyOTP/{actionType}/{sessionToken}")
  public String verifyOTP(
          HttpSession session,
          @PathVariable String sessionToken,
          RedirectAttributes redirectAttributes,
          Model model,
          @PathVariable String actionType
  ) {
    String token = (String) session.getAttribute("sessionToken");
    model.addAttribute("actionType", actionType);
    if (!sessionToken.equals(token) || !otpService.findSession(sessionToken)) {
      return "error";
    }
    String email = (String) session.getAttribute("email");
    if (email != null) {
      model.addAttribute("email", email);
    }
    return "verifyOTP";
  }

  @GetMapping("/resetPassword/{sessionToken}")
  public String resetPassword(
          HttpSession session,
          @PathVariable String sessionToken,
          Model model
  ) {
    try {
      String token = (String) session.getAttribute("sessionToken");
      Boolean isVerify = (Boolean) session.getAttribute("isVerify");
      if (!sessionToken.equals(token) || !isVerify) {
        return "error";
      }
      String user = (String) session.getAttribute("user_name");
      model.addAttribute("user", user);
      return "reset";
    } catch (Exception e) {
      return "error";
    }
  }
}