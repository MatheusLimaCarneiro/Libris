package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class RespostaForumRequestDTO {

    @NotNull(message = "O ID do perfil é obrigatório")
    private Integer perfilId;

    @NotEmpty(message = "O texto da resposta é obrigatório")
    private String texto;
}
