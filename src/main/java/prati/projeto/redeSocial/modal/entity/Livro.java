package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

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

    @Column(length = 80)
    @NotEmpty(message = "Campo título é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O título não pode conter apenas espaços.")
    private String titulo;

    @Column(length = 80)
    private String subtitulo;

    @Column(length = 80)
    @NotEmpty(message = "Campo autores é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "Os autores não podem conter apenas espaços.")
    private String autores;

    @Column(length = 50)
    @NotEmpty(message = "Campo editora é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "A editora não pode conter apenas espaços.")
    private String editora;

    @Column(length = 200)
    private String sinopse;

    @Column(name = "numeroPaginas")
    @NotNull(message = "Campo número de páginas é obrigatório")
    private int numeroPaginas;

    @Column(length = 50)
    @NotEmpty(message = "Campo ISBN é obrigatório")
    @Size(min = 10, max = 13, message = "O ISBN deve ter entre 10 e 13 caracteres")
    @Pattern(regexp = "\\d{10}|\\d{13}", message = "O ISBN deve conter apenas números")
    private String isbn;

    @Column(length = 20)
    @NotEmpty(message = "Campo idioma é obrigatório")
    private String idioma;

    @Column(length = 80)
    @NotEmpty(message = "Campo categoria é obrigatório")
    private String categoria;

    @Column(length = 500)
    @URL(message = "Campo URL da capa deve ser uma URL válida")
    @NotEmpty(message = "Campo URL da capa é obrigatório")
    private String url_capa;

    @Column(length = 500)
    @NotEmpty(message = "Campo link de compra é obrigatório")
    private String linkCompra;

    @NotNull(message = "Campo data de publicação é obrigatório")
    private LocalDate dataPublicacao;
}
