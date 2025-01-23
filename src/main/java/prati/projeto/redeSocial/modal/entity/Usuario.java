package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "usuario")
public class Usuario {

    @Id
    @Column(length = 100)
    @Email(message = "Campo de email é obrigatório")
    private String email;

    @Column(length = 50)
    @NotEmpty(message = "Campo nome é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O título não pode conter apenas espaços.")
    private String username;


    @NotEmpty(message = "Campo de senha é obrigatório")
    @Size(min = 7, message = "A senha deve ter no mínimo 7 caracteres")
    private String senha;
}
