package session.utils.Service;

import org.springframework.stereotype.Component;
import session.Account.AccountDAO;
import session.utils.PasswordEncryptor;
import session.utils.Service.EmailService.EmailService;

@Component
public class PasswordService {
    private final AccountDAO u;

    public PasswordService(AccountDAO u, EmailService emailService) {
        this.u = u;
    }

    //Account must be validate before update password


    //Get password from database
    public String getPassword(int user_id) {
        return u.findById(user_id).map(account -> {
            String dencrypt = null;
            try {
                return dencrypt = PasswordEncryptor.decryptPassword(account.getPassword());
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).orElse(" ");
    }

    //Check wheither password is correct
    public boolean validPassword(int user_id, String input) {
        return u.findById(user_id).map(account -> {
            try {
                return input.equals(PasswordEncryptor.decryptPassword(account.getPassword()));
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).orElse(false);
    }
}