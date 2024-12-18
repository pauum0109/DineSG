package session.Account;

import org.springframework.stereotype.Component;
import session.Account.DTO.UserDTO;
import session.Account.DTO.createUserDTO;

import session.utils.PasswordEncryptor;
import session.utils.State;
import session.utils.Enum.Status;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
@Component
public class AccountService {
    private final AccountDAO acc;
    public AccountService(AccountDAO dao) {
        this.acc = dao;
    }
    public List<UserDTO> getAllAccount() {
        return acc.findAll().map(accounts -> accounts.stream().map(UserDTO::fromEntity).collect(Collectors.toList())).orElse(Collections.emptyList());
    }
    public UserDTO findUser(int id) {
        return acc.findById(id).map(UserDTO::fromEntity).orElse(null);
    }

    public State<UserDTO> createAccount(createUserDTO accountDto) throws Exception {
        try {
            Account account = UserDTO.toEntity(accountDto);
            State<UserDTO> state = new State<>(UserDTO.fromEntity(account));
            if (acc.getByUsername(account.getUsername()).isPresent()) state.setStatus(Status.EXIST_USERNAME);
            else if (acc.getByEmail(account.getEmail()).isPresent()) state.setStatus(Status.EXIST_EMAIL);
            else {
                acc.save(account);
                state.setStatus(Status.SUCCESS);
            }
            ;
            return state;
        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }

    }


    public State<UserDTO> login(String user_name, String password) {
        State<UserDTO> state = new State<UserDTO>();
        Optional<Account> user = acc.getByUsername(user_name);
        if(user.isEmpty()||!PasswordEncryptor.verifyPassword(password, user.get().getPassword())) {
            state.setStatus(Status.ERROR);
            return state;
        };
        state.setStatus(Status.SUCCESS);
        state.setData(UserDTO.fromEntity(user.get()));
        return state;
    }
    public boolean isEmailExist(String email) {
        return acc.getByEmail(email).isPresent();
    }
    public Status updatePassword(String user_name, String input) {
        return acc.getByUsername(user_name).map(account -> {
            String encrypt;
            try {
                encrypt = PasswordEncryptor.encryptPassword(input);
                account.setPassword(encrypt);
                acc.update(account);
                return Status.SUCCESS;
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }).orElse(Status.ACCOUNT_NOT_FOUND);
    }
}