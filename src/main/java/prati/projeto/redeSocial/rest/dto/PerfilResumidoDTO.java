package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto que representa um perfil resumido com informações essenciais do usuário")
public class PerfilResumidoDTO {

    @Schema(
            description = "URL da imagem de perfil do usuário",
            example = "https://example.com/profile.jpg"
    )
    private String urlPerfil;

    @Schema(
            description = "Resumo da bio do usuário, até 120 caracteres",
            example = "Leitor ávido de ficção científica e fantasia."
    )
    private String resumoBio;

    @Schema(
            description = "Nome de usuário do perfil",
            example = "usuario"
    )
    private String username;
}
