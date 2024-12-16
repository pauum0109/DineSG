package session.OTP;

import org.springframework.stereotype.Component;
import session.Account.AccountDAO;
import session.Account.DTO.UserDTO;
import session.Account.Account;
import session.OTP.DAO.createOTP;
import session.utils.Service.EmailService.EmailService;
import session.utils.State;
import session.utils.Enum.Status;

//This service class is used to interact with the DAO class
@Component
public class OTPService {
    private final OTPDAO db;
    private final EmailService emailService;
    private final AccountDAO u;
    public OTPService(OTPDAO dao, EmailService emailService, AccountDAO u) {
        this.db = dao;
        this.emailService = emailService;
        this.u = u;
    }
    //Return wheither OTP exist or not
    public OTP getOTP(String sessionID) {
        return db.findById(sessionID).orElse(null);
    }
    private void createOTPDatabase(OTP o) {
        db.findById(o.getSessionID()).ifPresentOrElse(
                (otp) -> db.update(o),
                () -> db.save(o)
        );
    }
    /**
     * Client request sendOTP
     * Send OTP and add in table for verifying later
     */
    public Status sendOTPRecover(String sessionID, int userId) {
        Account account = u.findById(userId).orElse(null);
        createOTP otp = new createOTP(sessionID, account.getEmail());
        if (db.findById(otp.getSessionID()).isPresent()) return Status.ERROR;
        sendOTPVerify(otp.getSessionID(), account.getEmail());
        return Status.SUCCESS;
    }
    public void sendOTPVerify(String sessionID, String email) {
        OTP o = createOTP.toEnity(sessionID, email);
        emailService.sendOTP(email, o.getOtp());
        createOTPDatabase(o);
    }
    /**
     * Client request email for send otp to recovery
     * Case 1: if email not found, server send invalid email
     * Case 2: if email exist , go to input OTP code page return accountDto store in session
     */
    //
    public State<UserDTO> sendOTPRecovery(String sessionID, String email) {
        State<UserDTO> state = new State<>();
        //Nên verify email trước khi gửi OTP
        Account account = u.getByEmail(email).orElse(null);
        if (account == null) {
            state.setStatus(Status.NOT_FOUND);
            return state;
        }
        OTP o = createOTP.toEnity(sessionID, account.getEmail());
        createOTPDatabase(o);
        emailService.sendOTP(account.getUsername(), email, o.getOtp());
        state.setStatus(Status.SUCCESS);
        state.setData(UserDTO.fromEntity(account));
        return state;
    }

    /**
     * Client request email for send otp to recovery
     *Case 1: Check OTP code outdate, tell user outdate OTP and reuqire client to send OTP again
     * Case 2: if email exist , go to input OTP code page return accountDto store in session
     */
    public State<String> verifyOTP(String sessionID, int input) {
        State<String> state = new State<>();
        OTP o = db.findById(sessionID).orElse(null);
        // Check if OTP record is found
        // Check if OTP is valid (not expired or out of date)
        if (db.isOTPValid(sessionID).isEmpty()) {
            state.setStatus(Status.OUT_DATE);
            sendOTPRecovery(sessionID, o.getEmail());
            return state;
        }
        // Check if OTP matches the input
        if (o.getOtp() != input) {
            state.setStatus(Status.ERROR);
            return state;
        }
        state.setData(o.getEmail());
        db.delete(sessionID);
        state.setStatus(Status.SUCCESS);
        return state;
    }

    public boolean findSession(String sessionID) {
       return db.findById(sessionID).isPresent();
    }
}

//Verify OTP code from the user input and check with the database