package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
@Schema(
        name = "LivroResponseDTO",
        description = "DTO que representa a resposta de um livro no sistema. " +
                "Contém informações como o Google ID, título, subtítulo, autores, editora, sinopse, " +
                "número de páginas, ISBN, idioma, categoria, URL da capa, link de compra, faixa etária e data de publicação."
)
public class LivroResponseDTO {

    @Schema(
            description = "Google ID único do livro.",
            example = "1234567890"
    )
    @NotBlank(message = "O Google ID é obrigatório")
    private String googleId;

    @Schema(
            description = "Título do livro.",
            example = "Aprendendo Java"
    )
    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    @Schema(
            description = "Subtítulo do livro (opcional).",
            example = "Do básico ao avançado"
    )
    private String subtitulo;

    @Schema(
            description = "Autores do livro.",
            example = "João Silva, Maria Souza"
    )
    private String autores;

    @Schema(
            description = "Editora do livro.",
            example = "Editora Tech"
    )
    private String editora;

    @Schema(
            description = "Sinopse do livro (máximo de 4096 caracteres).",
            example = "Este livro aborda conceitos fundamentais de Java..."
    )
    @Size(max = 4096, message = "A sinopse deve ter no máximo 4096 caracteres")
    private String sinopse;

    @Schema(
            description = "Número de páginas do livro (deve ser um valor positivo).",
            example = "300"
    )
    @Positive(message = "O número de páginas deve ser um valor positivo")
    private int numeroPaginas;

    @Schema(
            description = "ISBN do livro.",
            example = "978-3-16-148410-0"
    )
    private String isbn;

    @Schema(
            description = "Idioma do livro.",
            example = "Português"
    )
    private String idioma;

    @Schema(
            description = "Categoria do livro.",
            example = "Programação"
    )
    private String categoria;

    @Schema(
            description = "URL da capa do livro (deve ser uma URL válida).",
            example = "https://exemplo.com/capa.jpg"
    )
    @NotBlank(message = "A URL da capa é obrigatória")
    @URL(message = "A URL da capa deve ser uma URL válida")
    private String urlCapa;

    @Schema(
            description = "Link de compra do livro (deve ser uma URL válida).",
            example = "https://exemplo.com/comprar"
    )
    @URL(message = "O link de compra deve ser uma URL válida")
    private String linkCompra;

    @Schema(
            description = "Faixa etária recomendada para o livro.",
            example = "12+"
    )
    private String faixaEtaria;

    @Schema(
            description = "Data de publicação do livro no formato 'yyyy-MM-dd'.",
            example = "2023-10-15"
    )
    private LocalDate dataPublicacao;
}