package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "RespostaDTO",
        description = "DTO que representa uma resposta a um comentário no sistema. " +
                "Contém informações como o ID da resposta, o ID do perfil que a criou, o texto da resposta, " +
                "a data de criação e a quantidade de curtidas."
)
public class RespostaDTO {

    @Schema(
            description = "ID único da resposta.",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "ID do perfil que criou a resposta.",
            example = "123",
            required = true
    )
    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @Schema(
            description = "Texto da resposta. Não pode estar vazio ou conter apenas espaços.",
            example = "Concordo com o seu ponto de vista!",
            required = true
    )
    @NotEmpty(message = "O texto da resposta é obrigatório.")
    @Pattern(regexp = ".*\\S.*", message = "O texto da resposta não pode conter apenas espaços.")
    private String texto;

    @Schema(
            description = "Data de criação da resposta no formato 'dd/MM/yyyy HH:mm'.",
            example = "15/10/2023 14:30"
    )
    private String dataResposta;

    @Schema(
            description = "Quantidade de curtidas que a resposta recebeu.",
            example = "10"
    )
    private Integer quantidadeCurtidas;
}