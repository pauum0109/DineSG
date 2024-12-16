package session.utils.Service.EmailService;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import session.Booking.DTO.CreateBookTableDTO;
import session.Restaurant.Restaurant;
import session.Restaurant.DAO.RestaurantDAO;
import session.userInformation.UserInformation;
import session.userInformation.UserInformationRepo;
import session.utils.Service.TimeConvert;

@Component
public class EmailService {

    //if email error return false
    private final JavaMailSender mailSender;
    private final RestaurantDAO restaurantDAO;
    private final UserInformationRepo userInformationRepo;
    public EmailService(JavaMailSender mailSender, RestaurantDAO restaurantDAO, UserInformationRepo userInformationRepo) {
        this.mailSender = mailSender;
        this.restaurantDAO = restaurantDAO;
        this.userInformationRepo = userInformationRepo;
    }
    //Implement Mail
    @Async

    public void sendOTP(String username, String email, int otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String subject = "Reset Password OTP";
            helper.setFrom("webproject123@gmail.com");
            helper.setFrom(new InternetAddress("lemanh1412@gmail.com", "Dev Support"));
            String body = "<!DOCTYPE html>" + "<html lan" +
                    "g=\"en\">" + "<head>" + "<meta charset=\"UTF-8\">" + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + "<title>OTP Email</title>" + "<style>" + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f6f6f6; }" + ".container { max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border: 1px solid #dddddd; }" + ".header { background-color: #4CAF50; color: white; text-align: center; padding: 10px 0; }" + ".content { padding: 20px; }"
                    + ".footer { text-align: center; color: #888888; font-size: 12px; padding: 20px; margin-top: 20px; border-top: 1px solid #dddddd; }"
                    + "p { line-height: 1.6; }" + "</style>" + "</head>" + "<body>" + "<div class=\"container\">"
                    + "  <div class=\"header\">" + "    <h1>Dev</h1>" + "  </div>" + "  <div class=\"content\">"
                    + "    <h2>Password Reset OTP</h2>" + "    <p>Dear <strong>" + username + "</strong></p>"
                    + "    <p>Your OTP is: <strong style=\"color: red;\">" + otp + "</strong></p>" + "    <p>Do not share this OTP with anyone. This OTP is <strong style=\"color: red;\">valid for 10 minutes</strong> .</p>"
                    + "    <p>If you did not request this OTP, please contact our support team.</p>" + "    <p>Best regards,</p>"
                    + "    <p>The Dev Team</p>" + "  </div>" + "  <div class=\"footer\">" + "    <p>Dev Inc. &copy; 2024</p>"
                    + "    <p>This email was sent to " + email + ". If you have any issues, please contact <a href=\"mailto:lemanh1412@gmail.com\">support@Dev.com</a>.</p>"
                    + "  </div>" + "</div>" + "</body>" + "</html>";

            helper.setTo(email);
            helper.setText(body, true);
            helper.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Async

    public void sendOTP(String email, int otp) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String subject = "Reset Password OTP";
            helper.setFrom("webproject123@gmail.com");
            helper.setFrom(new InternetAddress("lemanh1412@gmail.com", "Dev Support"));
            String body = "<!DOCTYPE html>" + "<html lan" +
                    "g=\"en\">" + "<head>" + "<meta charset=\"UTF-8\">" + "<meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">" + "<meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">" + "<title>OTP Email</title>" + "<style>" + "body { font-family: Arial, sans-serif; margin: 0; padding: 0; background-color: #f6f6f6; }" + ".container { max-width: 600px; margin: 20px auto; background-color: #ffffff; padding: 20px; border: 1px solid #dddddd; }" + ".header { background-color: #4CAF50; color: white; text-align: center; padding: 10px 0; }" + ".content { padding: 20px; }"
                    + ".footer { text-align: center; color: #888888; font-size: 12px; padding: 20px; margin-top: 20px; border-top: 1px solid #dddddd; }"
                    + "p { line-height: 1.6; }" + "</style>" + "</head>" + "<body>" + "<div class=\"container\">"
                    + "  <div class=\"header\">" + "    <h1>Dev</h1>" + "  </div>" + "  <div class=\"content\">"
                    + "    <h2>Verify Email Address</h2>" + "    <p>Dear " + "user" + "</p>"
                    + "    <p>Your OTP is: <strong style=\"color: red;\">" + otp + "</strong></p>" + "    <p>Do not share this OTP with anyone. This OTP is <strong style=\"color: red;\">valid for 10 minutes</strong> .</p>"
                    + "    <p>If you did not request this OTP, please contact our support team.</p>" + "    <p>Best regards,</p>"
                    + "    <p>The Dev Team</p>" + "  </div>" + "  <div class=\"footer\">" + "    <p>Dev Inc. &copy; 2024</p>"
                    + "    <p>This email was sent to " + email + ". If you have any issues, please contact <a href=\"mailto:lemanh1412@gmail.com\">support@Dev.com</a>.</p>"
                    + "  </div>" + "</div>" + "</body>" + "</html>";

            helper.setTo(email);
            helper.setText(body, true);
            helper.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    @Async

    public void sendConfirm(Restaurant restaurant, CreateBookTableDTO bookTableDTO) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String subject = "Booking Confirmation #"+bookTableDTO.getBooking_id();
            helper.setFrom("webproject123@gmail.com");
            helper.setFrom(new InternetAddress("lemanh1412@gmail.com", "Dev Support"));
            TimeConvert time= new TimeConvert(bookTableDTO.getTime());
            String body = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>Booking Confirmation</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            background-color: #f6f6f6;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 20px auto;\n" +
                    "            background-color: #ffffff;\n" +
                    "            padding: 20px;\n" +
                    "            border: 1px solid #dddddd;\n" +
                    "        }\n" +
                    "        .header {\n" +
                    "            background-color: #4CAF50;\n" +
                    "            color: white;\n" +
                    "            text-align: center;\n" +
                    "            padding: 10px 0;\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            padding: 20px;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            text-align: center;\n" +
                    "            color: #888888;\n" +
                    "            font-size: 12px;\n" +
                    "            padding: 20px;\n" +
                    "            margin-top: 20px;\n" +
                    "            border-top: 1px solid #dddddd;\n" +
                    "        }\n" +
                    "        p {\n" +
                    "            line-height: 1.6;\n" +
                    "        }\n" +
                    "        .details {\n" +
                    "            margin: 20px 0;\n" +
                    "            border-collapse: collapse;\n" +
                    "            width: 100%;\n" +
                    "        }\n" +
                    "        .details th, .details td {\n" +
                    "            border: 1px solid #dddddd;\n" +
                    "            padding: 8px;\n" +
                    "            text-align: left;\n" +
                    "        }\n" +
                    "        .details th {\n" +
                    "            background-color: #f2f2f2;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"header\">\n" +
                    "            <h1>Booking Confirmation</h1>\n" +
                    "        </div>\n" +
                    "        <div class=\"content\">\n" +
                    "            <p>Dear <strong>"+bookTableDTO.getName()+"</strong>,</p>\n" +
                    "            <p>Thank you for your reservation! Your booking details are as follows:</p>\n" +
                    "            <table class=\"details\">\n" +
                    "                <tr>\n" +
                    "                    <th>Booking ID</th>\n" +
                    "                    <td>"+bookTableDTO.getBooking_id()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Restaurant Name</th>\n" +
                    "                    <td>"+restaurant.getName()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Address</th>\n" +
                    "                    <td>"+restaurant.getAddress()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Time</th>\n" +
                    "                    <td>"+time.getTime()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Date</th>\n" +
                    "                    <td>"+time.getDay()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Customer Name</th>\n" +
                    "                    <td>"+bookTableDTO.getName()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Phone</th>\n" +
                    "                    <td>"+bookTableDTO.getPhone()+"</td>\n" +
                    "                </tr>\n" +
                    "                \n" +
                    "              \n" +
                    "                <tr>\n" +
                    "                    <th>Number of Guests</th>\n" +
                    "                    <td>"+bookTableDTO.getNumber_of_guests()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Note</th>\n" +
                    "                    <td>"+bookTableDTO.getNote()+"</td>\n" +
                    "                </tr>\n" +
                    "               \n" +
                    "            </table>\n" +
                    "            <p>If you have need to modify your reservation, please update directly in the Booking Section.</p>\n" +
                    "            <p>We look forward to serving you!</p>\n" +
                    "            <p>Best regards,</p>\n" +
                    "            <p>The "+restaurant.getName()+" Team</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"footer\">\n" +
                    "            <p>&copy; 2024 "+restaurant.getName()+". All rights reserved.</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";

            helper.setTo(bookTableDTO.getEmail());
            helper.setText(body, true);
            helper.setSubject(subject);
            mailSender.send(message);

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
    @Async
    public void sendAnnounce( CreateBookTableDTO bookTableDTO) {

        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            String subject = "New Booking Order arrive #"+bookTableDTO.getBooking_id();
            helper.setFrom("webproject123@gmail.com");
            helper.setFrom(new InternetAddress("lemanh1412@gmail.com", "Dev Support"));
            Restaurant restaurant= restaurantDAO.findById(bookTableDTO.getRestaurant_id()).orElse(null);
            UserInformation user = userInformationRepo.getUserByRestaurant(bookTableDTO.getRestaurant_id());
            TimeConvert time= new TimeConvert(bookTableDTO.getTime());
            String body = "<!DOCTYPE html>\n" +
                    "<html lang=\"en\">\n" +
                    "<head>\n" +
                    "    <meta charset=\"UTF-8\">\n" +
                    "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                    "    <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                    "    <title>New Booking Table Arrive</title>\n" +
                    "    <style>\n" +
                    "        body {\n" +
                    "            font-family: Arial, sans-serif;\n" +
                    "            margin: 0;\n" +
                    "            padding: 0;\n" +
                    "            background-color: #f6f6f6;\n" +
                    "        }\n" +
                    "        .container {\n" +
                    "            max-width: 600px;\n" +
                    "            margin: 20px auto;\n" +
                    "            background-color: #ffffff;\n" +
                    "            padding: 20px;\n" +
                    "            border: 1px solid #dddddd;\n" +
                    "        }\n" +
                    "        .header {\n" +
                    "            background-color: #4CAF50;\n" +
                    "            color: white;\n" +
                    "            text-align: center;\n" +
                    "            padding: 10px 0;\n" +
                    "        }\n" +
                    "        .content {\n" +
                    "            padding: 20px;\n" +
                    "        }\n" +
                    "        .footer {\n" +
                    "            text-align: center;\n" +
                    "            color: #888888;\n" +
                    "            font-size: 12px;\n" +
                    "            padding: 20px;\n" +
                    "            margin-top: 20px;\n" +
                    "            border-top: 1px solid #dddddd;\n" +
                    "        }\n" +
                    "        p {\n" +
                    "            line-height: 1.6;\n" +
                    "        }\n" +
                    "        .details {\n" +
                    "            margin: 20px 0;\n" +
                    "            border-collapse: collapse;\n" +
                    "            width: 100%;\n" +
                    "        }\n" +
                    "        .details th, .details td {\n" +
                    "            border: 1px solid #dddddd;\n" +
                    "            padding: 8px;\n" +
                    "            text-align: left;\n" +
                    "        }\n" +
                    "        .details th {\n" +
                    "            background-color: #f2f2f2;\n" +
                    "        }\n" +
                    "    </style>\n" +
                    "</head>\n" +
                    "<body>\n" +
                    "    <div class=\"container\">\n" +
                    "        <div class=\"header\">\n" +
                    "            <h1>New Booking Table Arrive</h1>\n" +
                    "        </div>\n" +
                    "        <div class=\"content\">\n" +
                    "            <p>Dear <strong>"+user.getFullName()+"</strong>,</p>\n" +
                    "            <p>New order arrive for restaurant "+restaurant.getName()+"</p>\n" +
                    "            <table class=\"details\">\n" +
                    "                <tr>\n" +
                    "                    <th>Booking ID</th>\n" +
                    "                    <td>"+bookTableDTO.getBooking_id()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Restaurant Name</th>\n" +
                    "                    <td>"+restaurant.getName()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Address</th>\n" +
                    "                    <td>"+restaurant.getAddress()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Time</th>\n" +
                    "                    <td>"+time.getTime()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Date</th>\n" +
                    "                    <td>"+time.getDay()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Customer Name</th>\n" +
                    "                    <td>"+bookTableDTO.getName()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Phone</th>\n" +
                    "                    <td>"+bookTableDTO.getPhone()+"</td>\n" +
                    "                </tr>\n" +
                    "                \n" +
                    "              \n" +
                    "                <tr>\n" +
                    "                    <th>Number of Guests</th>\n" +
                    "                    <td>"+bookTableDTO.getNumber_of_guests()+"</td>\n" +
                    "                </tr>\n" +
                    "                <tr>\n" +
                    "                    <th>Note</th>\n" +
                    "                    <td>"+bookTableDTO.getNote()+"</td>\n" +
                    "                </tr>\n" +
                    "               \n" +
                    "            </table>\n" +
                    "            <p>If you need to reply to this booking, please update directly in the Owner Booking Section.</p>\n" +
                    "            <p>We look forward to serving you!</p>\n" +
                    "            <p>Best regards,</p>\n" +
                    "            <p>The Dev Team</p>\n" +
                    "        </div>\n" +
                    "        <div class=\"footer\">\n" +
                    "            <p>&copy; 2024 Dev team. All rights reserved.</p>\n" +
                    "        </div>\n" +
                    "    </div>\n" +
                    "</body>\n" +
                    "</html>\n";
            helper.setTo(user.getEmail());
            helper.setText(body, true);
            helper.setSubject(subject);
            mailSender.send(message);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
}