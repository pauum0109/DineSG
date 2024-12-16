package session.utils.Service;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
@Getter
@Setter

public class TimeConvert {
    private String time;
    private String day;
    public TimeConvert(String input){
        convert(input);
    }
    // Conversion method
    private void convert(String input) {
        try {
            // Parse the input string
            LocalDateTime dateTime = LocalDateTime.parse(input);

            // Format time as "HH:mm"
            DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm");
            this.time = dateTime.format(timeFormatter);

            // Format day as "dd/MM/yy"
            DateTimeFormatter dayFormatter = DateTimeFormatter.ofPattern("dd/MM/yy");
            this.day = dateTime.format(dayFormatter);

        } catch (Exception e) {
            System.out.println("Invalid input format. Please use the format 'yyyy-MM-dd'T'HH:mm'");
            e.printStackTrace();
        }
    }
}