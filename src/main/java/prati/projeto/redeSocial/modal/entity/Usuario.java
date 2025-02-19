package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "usuario")
@Schema(description = "Entidade Usuário")
public class Usuario {

    @Id
    @Column(length = 100)
    @NotEmpty(message = "Campo email é obrigatório")
    @Email(message = "Campo de email tem quer ser valido")
    @Schema(description = "Email do usuário", example = "usuario@example.com")
    private String email;

    @Column(length = 50)
    @NotEmpty(message = "Campo nome é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O título não pode conter apenas espaços.")
    @Schema(description = "Nome de usuário", example = "usuario")
    private String username;

    @Column(nullable = true)
    @Schema(description = "Senha do usuário", example = "senha123")
    private String senha;

    @Column(name = "is_admin")
    @Schema(description = "Indica se o usuário é administrador", example = "false")
    private boolean admin = false;

    @Schema(description = "Provedor de autenticação do usuário", example = "google")
    private String authProvider;

    @Column(name = "reset_token", length = 2000)
    @Schema(description = "Token para redefinição de senha", example = "abc123xyz")
    private String resetToken;
}
