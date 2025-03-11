package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para alteração de senha")
public class ChangePasswordDTO {

    @NotBlank(message = "A senha atual não pode estar em branco")
    private String oldPassword;

    @Schema(description = "Nova senha do usuário", example = "novaSenha456")
    @NotBlank(message = "A nova senha não pode estar em branco")
    private String newPassword;
}