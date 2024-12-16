package session.responseHandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;


import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@Getter
@JsonPropertyOrder({"statusCode", "time","data","message","sqlMessage"})
@JsonInclude(JsonInclude.Include.NON_NULL)
public class BodyResponseWithTime<T> extends BodyResponse<T> {
    private final String time;
    public BodyResponseWithTime(T data, int statusCode) {
        super(data,statusCode);
        this.time =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));;
    }
    public BodyResponseWithTime(T data,String mess, int statusCode) {
        super(data,mess,statusCode);
        this.time =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
    public BodyResponseWithTime(String mess, int statusCode) {
        super(mess,statusCode);
        this.time =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public BodyResponseWithTime(int statusCode) {
        super(statusCode);
        this.time =  LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }
}