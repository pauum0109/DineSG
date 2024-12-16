package session.Account.DTO;


import java.io.Serializable;

import session.Account.Account;
import session.utils.PasswordEncryptor;


public record UserDTO(int id, String username, String email, String role,String phone,String full_name ) implements Serializable {
    public static UserDTO fromEntity(Account save) {
        return new UserDTO(save.getUser_id(), save.getUsername(), save.getEmail(), save.getCreatedAt(),save.getPhone_number(),save.getFull_name());
    }
    public static Account toEntity(createUserDTO model) throws Exception {
        Account account = new Account();
        int code = (int) (Math.random() * 9000) + 1000;
        account.setUser_id(code);
        account.setUsername(model.getUsername());
        account.setPassword(PasswordEncryptor.encryptPassword(model.getPassword()));
        account.setEmail(model.getEmail());
        account.setRole(model.getRole());
        return account;
    }
};