package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
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

    @NotEmpty(message = "Campo de senha é obrigatório")
    @Size(min = 7, message = "A senha deve ter no mínimo 7 caracteres")
    @Schema(description = "Senha do usuário", example = "senha123")
    private String senha;

    @Column(name = "is_admin")
    @Schema(description = "Indica se o usuário é administrador", example = "false")
    private boolean admin = false;
}
