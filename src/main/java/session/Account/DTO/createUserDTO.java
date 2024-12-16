package session.Account.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class createUserDTO {
    private String username;
    private String password;
    private String email;
    private String role;
    public createUserDTO(String username, String password, String email, String role) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.role = role;
    }

}