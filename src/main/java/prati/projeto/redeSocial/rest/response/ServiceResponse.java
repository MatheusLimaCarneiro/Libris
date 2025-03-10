package prati.projeto.redeSocial.rest.response;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Resposta genérica da API")
public class ServiceResponse<T> {
    @Schema(description = "Dados da resposta")
    private T data;

    @Schema(description = "Mensagem descritiva da resposta", example = "Operação realizada com sucesso")
    private String message;

    @Schema(description = "Indica se a operação foi bem-sucedida", example = "true")
    private boolean success;

    @Schema(description = "Timestamp da resposta", example = "2025-03-07 14:59:36")
    private String timestamp;

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
