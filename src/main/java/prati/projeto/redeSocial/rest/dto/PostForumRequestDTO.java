package prati.projeto.redeSocial.rest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PostForumRequestDTO {

    @NotBlank(message = "O texto do post não pode estar vazio.")
    private String texto;

    @Size(max = 100, message = "As tags não podem ter mais de 100 caracteres.")
    private String tags;

    @NotNull(message = "O campo possuiSpoiler é obrigatório.")
    private Boolean possuiSpoiler;

    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @NotEmpty(message = "Informe o código do livro")
    private String googleId;
}
