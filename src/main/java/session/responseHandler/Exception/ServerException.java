package session.responseHandler.Exception;

import lombok.Getter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

//Def
@Getter
public class ServerException extends ResponseStatusException {
    private  HttpHeaders headers;
    private final String messageR;
    public ServerException(String messageR, HttpHeaders headers) {
        super(HttpStatus.BAD_GATEWAY, messageR);
        this.headers = headers;
        this.messageR = messageR;
    }
    public ServerException(String messageR) {
        super(HttpStatus.BAD_GATEWAY, messageR);
        this.messageR = messageR;
    }

    public HttpHeaders getHeader() {
        return headers;
    }


}