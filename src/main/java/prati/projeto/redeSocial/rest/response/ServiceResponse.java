package prati.projeto.redeSocial.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ServiceResponse<T> {
    private T data;
    private String message;
    private boolean success;
    private String timestamp ;

    public ServiceResponse(T data, String message, boolean success) {
        this.data = data;
        this.message = message;
        this.success = success;
        this.timestamp = formatTimestamp(LocalDateTime.now());
    }

    private String formatTimestamp(LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return dateTime.format(formatter);
    }
}
