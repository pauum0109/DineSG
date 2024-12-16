package session.responseHandler.Exception;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@JsonPropertyOrder({"Status", "time", "message",})
public record ExceptionBodyModel(int status, String message, String time) {
    public ExceptionBodyModel(int status, String message) {
        this(status,message,LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    }
}