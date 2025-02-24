package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ComentarioForumRequestDTO {

    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @NotBlank(message = "O texto do comentário não pode estar vazio.")
    @Size(max = 1000, message = "O texto do comentário não pode ter mais de 1000 caracteres.")
    private String texto;

    @NotNull(message = "O campo spoiler é obrigatório.")
    private boolean spoiler;
}
