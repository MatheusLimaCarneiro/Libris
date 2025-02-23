package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDateTime;

/**
 * DTO para representação de uma resposta.
 * <p>
 * Este objeto encapsula as informações de uma resposta, incluindo o identificador do perfil que a criou,
 * o texto da resposta e a data em que foi realizada.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representação de uma resposta")
public class RespostaDTO {


    @Schema(
            description = "Identificador único da resposta",
            example = "1"
    )
    private Integer id;


    @NotNull(message = "O ID do perfil é obrigatório.")
    @Schema(
            description = "Identificador do perfil que criou a resposta",
            example = "10"
    )
    private Integer perfilId;


    @NotEmpty(message = "O texto da resposta é obrigatório.")
    @Pattern(regexp = ".*\\S.*", message = "O texto da resposta não pode conter apenas espaços.")
    @Schema(
            description = "Texto da resposta",
            example = "Concordo com essa análise."
    )
    private String texto;


    @Schema(
            description = "Data e hora em que a resposta foi realizada",
            example = "2025-02-23T15:30:00"
    )
    private LocalDateTime dataResposta;
}
