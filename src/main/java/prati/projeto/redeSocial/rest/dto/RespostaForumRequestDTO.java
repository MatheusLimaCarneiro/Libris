package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
        name = "RespostaForumRequestDTO",
        description = "DTO que representa a requisição para criar uma resposta no fórum. " +
                "Contém o ID do perfil e o texto da resposta."
)
public class RespostaForumRequestDTO {

    @Schema(
            description = "ID do perfil que está criando a resposta.",
            example = "1"
    )
    @NotNull(message = "O ID do perfil é obrigatório")
    private Integer perfilId;

    @Schema(
            description = "Texto da resposta. Não pode estar vazio.",
            example = "Concordo com o seu ponto de vista!"
    )
    @NotEmpty(message = "O texto da resposta é obrigatório")
    private String texto;
}