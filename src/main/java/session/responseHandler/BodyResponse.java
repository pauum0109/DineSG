package session.responseHandler;
import lombok.Getter;
@Getter
public class BodyResponse<T> {
    private T data;
    private String message;
    private final int statusCode;

    public BodyResponse(T data, int statusCode) {
        this.data = data;
        this.statusCode = statusCode;
    }
    public BodyResponse(String message, int statusCode) {
        this.message = message;
        this.statusCode = statusCode;
    }
    public BodyResponse(T data, String message, int statusCode) {
        this.message = message;
        this.data = data;
        this.statusCode = statusCode;
    }
    public BodyResponse(int statusCode){
        this.statusCode = statusCode;
    }
}