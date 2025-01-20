package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "livro")
public class Livro {

    @Id
    @NotNull(message = "O campo ID é obrigatório.")
    private Integer id;

    @Column(length = 100)
    @NotEmpty(message = "Campo título é obrigatório")
    private String titulo;

    @Column(length = 100)
    @NotEmpty(message = "Campo autor é obrigatório")
    private String autor;

    @Column(length = 13, unique = true)
    @NotEmpty(message = "Campo ISBN é obrigatório")
    @Size(min = 13, max = 13, message = "O ISBN deve ter exatamente 13 caracteres")
    private String isbn;

    @Column(length = 2000)
    private String sinopse;

    @NotNull(message = "Campo data de publicação é obrigatório")
    private LocalDate dataPublicacao;
}
