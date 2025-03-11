package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Schema(
        name = "PostForumRequestDTO",
        description = "DTO que representa a requisição para criar um novo post no fórum. " +
                "Contém informações como o texto do post, tags, indicação de spoiler, ID do perfil e código do livro."
)
public class PostForumRequestDTO {

    @Schema(
            description = "Texto do post. Não pode estar vazio.",
            example = "Este livro mudou minha vida!"
    )
    @NotBlank(message = "O texto do post não pode estar vazio.")
    private String texto;

    @Schema(
            description = "Tags associadas ao post. Não pode ter mais de 100 caracteres.",
            example = "ficção,aventura"
    )
    @Size(max = 100, message = "As tags não podem ter mais de 100 caracteres.")
    private String tags;

    @Schema(
            description = "Indica se o post contém spoiler.",
            example = "true"
    )
    @NotNull(message = "O campo possuiSpoiler é obrigatório.")
    private Boolean possuiSpoiler;

    @Schema(
            description = "ID do perfil que está criando o post.",
            example = "1"
    )
    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @Schema(
            description = "Código do livro associado ao post (Google ID).",
            example = "XYZ123"
    )
    @NotEmpty(message = "Informe o código do livro")
    private String googleId;
}