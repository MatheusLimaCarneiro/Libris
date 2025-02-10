package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Objeto que representa o token de autenticação e o refresh token de um usuário.")
public class TokenDTO {

    @Schema(
            description = "Nome de usuário ou email utilizado para a autenticação.",
            example = "usuario@example.com"
    )
    private String login;

    @Schema(
            description = "Token de autenticação JWT utilizado para acessar endpoints protegidos.",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMjM0NTY3ODkwLCJleHBpcmF0aW9uIjoiMjAyMy0wMi0wOFQxMjpM... (token)"
    )
    private String token;

    @Schema(
            description = "Refresh token utilizado para renovar o token de autenticação sem a necessidade de realizar login novamente.",
            example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJleHBpcmF0aW9uIjoiMjAyMy0wMi0wOFQxMjpM... (refresh token)"
    )
    private String refreshToken;
}
