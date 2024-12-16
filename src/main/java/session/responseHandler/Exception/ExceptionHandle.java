package session.responseHandler.Exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import session.responseHandler.BodyResponseError;
import session.responseHandler.BodyResponseWithTime;

// @Only one @ControllerAdvice
@ControllerAdvice
public class ExceptionHandle extends Throwable {
    @ExceptionHandler(value = { Exception.class })
    public ResponseEntity<Object> handleException(Exception ex, WebRequest request) {
        String SQLmessage = ex.getMessage();
        return new ResponseEntity<>(new BodyResponseWithTime<>("Error", HttpStatus.CREATED.value()), HttpStatus.BAD_REQUEST);
    }
    //Define the exception handler for ExistException,
    //ExistException ex will be found and plugin
    //Throw new theo format of BodyResponseError
    @ExceptionHandler(value = { ExistException.class})
    public ResponseEntity<Object> handleException(ExistException ex) {
        return new ResponseEntity<>(new BodyResponseWithTime<>(ex.getReason(),ex.getStatusCode().value()), ex.getHeader(), ex.getStatusCode());
    }
    @ExceptionHandler(value = { ServerException.class})
    public ResponseEntity<Object> exception(ServerException e) {
        return new ResponseEntity<>(new BodyResponseError<>(e.getReason(),e.getStatusCode().value()), e.getHeader(), e.getStatusCode());
    }
}