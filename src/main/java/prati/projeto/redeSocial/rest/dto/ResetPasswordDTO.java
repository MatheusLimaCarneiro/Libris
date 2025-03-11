package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Objeto que representa a confirmação de redefinição de senha.")
public class ResetPasswordDTO {

    @NotEmpty(message = "O token não pode estar vazio.")
    @Schema(description = "Token de redefinição de senha enviado por e-mail", example = "abcd1234xyz")
    private String token;

    @NotEmpty(message = "A nova senha não pode estar vazia.")
    @Schema(description = "Nova senha escolhida pelo usuário", example = "novaSenha123")
    private String newPassword;
}
