package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaDTO {

    private Integer id;

    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @NotEmpty(message = "O texto da resposta é obrigatório.")
    @Pattern(regexp = ".*\\S.*", message = "O texto da resposta não pode conter apenas espaços.")
    private String texto;

    private LocalDateTime dataResposta;
}
