package prati.projeto.redeSocial.modal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidade representando um livro na plataforma, com dados como título, autores, editora, ISBN, etc.")
public class Livro {

    @Id
    @Schema(description = "ID único do livro", example = "1")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    @Schema(description = "Google ID do livro", example = "abc123")
    private String googleId;

    @Column(length = 80)
    @NotEmpty(message = "Campo título é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O título não pode conter apenas espaços.")
    @Schema(description = "Título do livro", example = "O Senhor dos Anéis")
    private String titulo;

    @Column(length = 80)
    @Schema(description = "Subtítulo do livro", example = "A Sociedade do Anel")
    private String subtitulo;

    @Column(length = 80)
    @Pattern(regexp = ".*\\S.*", message = "Os autores não podem conter apenas espaços.")
    @Schema(description = "Autores do livro", example = "J.R.R. Tolkien")
    private String autores;

    @Column(length = 50)
    @Pattern(regexp = ".*\\S.*", message = "A editora não pode conter apenas espaços.")
    @Schema(description = "Nome da editora", example = "HarperCollins")
    private String editora;

    @Column(length = 4096)
    @Size(max = 4096, message = "A sinopse deve ter no máximo 4096 caracteres")
    @Schema(description = "Sinopse do livro", example = "Uma aventura épica em uma terra fantástica.")
    private String sinopse;

    @Column(name = "numeroPaginas")
    @Schema(description = "Número de páginas do livro", example = "1200")
    private int numeroPaginas;

    @Column(length = 50)
    @Schema(description = "ISBN do livro", example = "9780261103573")
    private String isbn;

    @Column(length = 20)
    @Schema(description = "Idioma do livro", example = "Português")
    private String idioma;

    @Column(length = 1024)
    @Schema(description = "Categoria do livro", example = "Ficção Fantástica")
    private String categoria;

    @Column(length = 500)
    @URL(message = "A URL da capa deve ser uma URL válida")
    @NotEmpty(message = "Campo URL da capa é obrigatório")
    @Schema(description = "URL da capa do livro", example = "https://example.com/capa.jpg")
    private String url_capa;

    @Column(length = 500)
    @Schema(description = "Link para compra do livro", example = "https://example.com/comprar")
    private String linkCompra;

    @Schema(description = "Data de publicação do livro", example = "1954-07-29")
    private LocalDate dataPublicacao;

    @Column(name = "media_livro")
    @Schema(description = "Média com base na avaliação do livro")
    private Integer media = 0;
}
