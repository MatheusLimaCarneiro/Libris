package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForumRequestDTO {

    @NotBlank(message = "O texto do post não pode estar vazio.")
    private String texto;

    private String tags;

    private Boolean possuiSpoiler;

    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @NotNull(message = "O ID do livro é obrigatório.")
    private Integer livroId;
}
