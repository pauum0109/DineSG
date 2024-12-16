package session.responseHandler.Exception;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
//Def
@Getter
public class ExistException extends ResponseStatusException {
    private final HttpHeaders headers;
    private final String messageR = "Account Exist";

    public ExistException(String messageR, HttpHeaders headers) {
        super(HttpStatus.NOT_FOUND, messageR);
        this.headers = headers;
    }
    public HttpHeaders getHeader() {
        return headers;
    }


}