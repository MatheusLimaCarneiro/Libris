package prati.projeto.redeSocial.rest.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiResponse<T> {
    private T data;
    private String message;
    private boolean success;
    private LocalDateTime timestamp = LocalDateTime.now();
}
