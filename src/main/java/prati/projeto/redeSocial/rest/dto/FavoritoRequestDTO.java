package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class FavoritoRequestDTO {

    @NotNull(message = "O ID do perfil é obrigatório")
    private Integer perfilId;

    @NotNull(message = "O Google ID do livro é obrigatório")
    private String googleId;
}
