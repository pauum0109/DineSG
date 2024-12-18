package session.Account;


import lombok.Getter;
import lombok.Setter;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.lang.Nullable;

import java.sql.ResultSet;
import java.sql.SQLException;
@Getter
@Setter
public class Account implements RowMapper<Account> {
private int user_id;
private String username;
private String password;
private String email;
private String role;
private String full_name;
private String phone_number;
private  String address;
private String profile_picture_url;
private String createdAt;

@Override
@Nullable
public Account mapRow(ResultSet arg0, int arg1) throws SQLException {
    Account res = new Account();
    res.setUser_id(arg0.getInt("user_id"));
    res.setUsername(arg0.getString("username"));
    res.setPassword(arg0.getString("user_password"));
    res.setEmail(arg0.getString("user_email"));
    res.setCreatedAt(arg0.getString("created_at"));
    res.setRole(arg0.getString("user_role"));
    return res;
}
}