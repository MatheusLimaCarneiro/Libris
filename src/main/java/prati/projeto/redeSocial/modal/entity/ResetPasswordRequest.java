package prati.projeto.redeSocial.modal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "Entidade que representa a solicitação de redefinição de senha.")
public class ResetPasswordRequest {

    @NotEmpty(message = "O e-mail não pode ser vazio.")
    @Email(message = "O e-mail fornecido é inválido.")
    @Schema(description = "E-mail cadastrado do usuário para envio do token de redefinição", example = "usuario@example.com")
    private String email;
}
