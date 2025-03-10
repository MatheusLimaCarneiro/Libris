package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "DTO para alteração de senha")
public class ChangePasswordDTO {

    @Schema(description = "Senha atual do usuário", example = "senhaAtual123")
    private String oldPassword;

    @Schema(description = "Nova senha do usuário", example = "novaSenha456")
    private String newPassword;
}