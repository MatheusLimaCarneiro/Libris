package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CredenciaisDTO {

    @NotEmpty(message = "O campo login é obrigatório.")
    private String login;

    @NotEmpty(message = "O campo senha é obrigatório.")
    private String senha;
}
