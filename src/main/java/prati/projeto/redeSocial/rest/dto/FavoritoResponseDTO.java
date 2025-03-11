package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Schema(
        name = "FavoritoResponseDTO",
        description = "DTO que representa a resposta de um livro favorito. " +
                "Contém informações como o ID do favorito, username do perfil, Google ID do livro, " +
                "dados resumidos do livro e a data em que o livro foi favoritado."
)
public class FavoritoResponseDTO {

    @Schema(
            description = "ID do favorito.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Schema(
            description = "Username do perfil que favoritou o livro.",
            example = "usuario123",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String username;

    @Schema(
            description = "Google ID do livro favoritado.",
            example = "XYZ123",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private String googleId;

    @Schema(
            description = "Dados resumidos do livro favoritado.",
            implementation = LivroResumidoDTO.class,
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LivroResumidoDTO livro;

    @Schema(
            description = "Data e hora em que o livro foi favoritado.",
            example = "2023-10-01T12:34:56",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private LocalDateTime dataFavorito;
}