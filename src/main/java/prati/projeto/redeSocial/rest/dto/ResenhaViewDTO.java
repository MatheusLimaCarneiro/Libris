package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
@Schema(
        name = "ResenhaViewDTO",
        description = "DTO que representa uma visualização detalhada de uma resenha de livro no sistema. " +
                "Contém informações como o ID da resenha, ID do perfil que fez a resenha, detalhes do livro, " +
                "Google ID do livro, título da resenha, autor do livro, texto da resenha, datas de publicação e edição, " +
                "nota atribuída, indicador de spoiler, média das avaliações e a lista de avaliações associadas."
)
public class ResenhaViewDTO {

    @Schema(
            description = "ID da resenha.",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "ID do perfil que fez a resenha.",
            example = "1"
    )
    private Integer perfilId;

    @Schema(
            description = "Detalhes do livro associado à resenha.",
            implementation = LivroResumidoDTO.class
    )
    private LivroResumidoDTO livro;

    @Schema(
            description = "Google ID do livro associado à resenha.",
            example = "1234567890"
    )
    private String googleId;

    @Schema(
            description = "Título da resenha.",
            example = "Uma análise profunda sobre o livro"
    )
    private String titulo;

    @Schema(
            description = "Nome do autor do livro.",
            example = "João Silva"
    )
    private String autor;

    @Schema(
            description = "Texto da resenha.",
            example = "Este livro é incrível, recomendo a todos!"
    )
    private String texto;

    @Schema(
            description = "Data de publicação da resenha.",
            example = "2023-10-05T12:34:56.789Z"
    )
    private String dataPublicacao;

    @Schema(
            description = "Data da última edição da resenha.",
            example = "2023-10-06T10:15:30.456Z"
    )
    private String dataEdicao;

    @Schema(
            description = "Nota atribuída ao livro (deve ser entre 1 e 5).",
            example = "4.5"
    )
    private Double nota;

    @Schema(
            description = "Indica se a resenha contém spoilers.",
            example = "true"
    )
    private boolean spoiler;

    @Schema(
            description = "Média das avaliações recebidas pela resenha.",
            example = "4.2"
    )
    private Double media;

    @Schema(
            description = "Lista de avaliações associadas à resenha.",
            implementation = AvaliacaoDTO.class
    )
    private List<AvaliacaoDTO> avaliacoes;
}