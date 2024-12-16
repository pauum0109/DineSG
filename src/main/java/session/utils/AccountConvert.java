package session.utils;

import session.Account.Account;
import session.Account.DTO.createUserDTO;

public class AccountConvert {
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



}