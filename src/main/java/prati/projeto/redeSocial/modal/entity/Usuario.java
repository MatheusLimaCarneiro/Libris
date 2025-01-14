package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
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
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(length = 50)
    @NotEmpty(message = "Campo nome é obrigatório")
    private String username;

    @Column(length = 100)
    @Email(message = "Campo de email é obrigatório")
    private String email;

    @NotEmpty(message = "Campo de senha é obrigatório")
    private String senha;
}
