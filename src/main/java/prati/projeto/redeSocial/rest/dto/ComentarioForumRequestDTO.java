package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
@Schema(
        name = "ComentarioForumRequestDTO",
        description = "DTO que representa a requisição para criar um novo comentário no fórum. " +
                "Contém informações como o ID do perfil, o texto do comentário e uma indicação de spoiler."
)
public class ComentarioForumRequestDTO {

    @Schema(
            description = "ID do perfil que está criando o comentário.",
            example = "1"
    )
    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @Schema(
            description = "Texto do comentário. Não pode estar vazio e deve ter no máximo 1000 caracteres.",
            example = "Este post é incrível!"
    )
    @NotBlank(message = "O texto do comentário não pode estar vazio.")
    @Size(max = 1000, message = "O texto do comentário não pode ter mais de 1000 caracteres.")
    private String texto;

    @Schema(
            description = "Indica se o comentário contém spoiler.",
            example = "true"
    )
    @NotNull(message = "O campo spoiler é obrigatório.")
    private boolean spoiler;
}