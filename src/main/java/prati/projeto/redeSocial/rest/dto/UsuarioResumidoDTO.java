package prati.projeto.redeSocial.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;

@Data
@AllArgsConstructor
@Schema(description = "DTO com informações resumidas de um usuário")
public class UsuarioResumidoDTO {
    @Schema(description = "Nome de usuário", example = "usuario")
    private String username;

    @Schema(description = "Email do usuário", example = "usuario@example.com")
    private String email;
}
