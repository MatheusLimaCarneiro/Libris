package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.NotEmpty;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para credenciais de login")
public class CredenciaisDTO {

    @NotEmpty(message = "O campo login é obrigatório.")
    @Schema(description = "Login do usuário", example = "usuario")
    private String login;

    @NotEmpty(message = "O campo senha é obrigatório.")
    @Schema(description = "Senha do usuário", example = "senha123")
    private String senha;
}
