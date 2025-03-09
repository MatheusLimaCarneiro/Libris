package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
@Schema(
        name = "FavoritoRequestDTO",
        description = "DTO que representa a requisição para favoritar um livro. " +
                "Contém informações como o ID do perfil e o Google ID do livro."
)
public class FavoritoRequestDTO {

    @Schema(
            description = "ID do perfil que está favoritando o livro.",
            example = "1",
            required = true
    )
    @NotNull(message = "O ID do perfil é obrigatório")
    private Integer perfilId;

    @Schema(
            description = "Google ID do livro que está sendo favoritado.",
            example = "XYZ123",
            required = true
    )
    @NotNull(message = "O Google ID do livro é obrigatório")
    private String googleId;
}