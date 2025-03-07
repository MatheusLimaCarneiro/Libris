package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
@Schema(description = "DTO representando um livro resumido, com título, autor e data de publicação.")
public class LivroResumidoDTO {

    @Schema(description = "Título do livro", example = "O Senhor dos Anéis")
    private String titulo;

    @Schema(description = "Autor(es) do livro", example = "J.R.R. Tolkien")
    private String autor;

    @Schema(description = "Data de publicação do livro", example = "1954-07-29")
    private String dataPublicacao;
}
