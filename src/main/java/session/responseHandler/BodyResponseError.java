package session.responseHandler;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;

@Getter
@JsonPropertyOrder({"statusCode", "time","sqlMessage"})
public class BodyResponseError<T>  extends BodyResponseWithTime<T> {
    private final String sqlMessage;

    public BodyResponseError(String sqlMessage, int statusCode ) {
        super(statusCode);
        this.sqlMessage = sqlMessage;
    }
}