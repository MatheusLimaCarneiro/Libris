package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

/**
 * DTO para visualização de uma resenha.
 * <p>
 * Este objeto encapsula os dados detalhados de uma resenha, incluindo informações
 * sobre o livro associado, os dados da resenha, datas de publicação e edição,
 * a nota atribuída e as avaliações associadas.
 * </p>
 */
@Data
@AllArgsConstructor
@Schema(description = "DTO para visualização de uma resenha")
public class ResenhaViewDTO {


    @Schema(
            description = "Identificador único da resenha",
            example = "1"
    )
    private Integer id;


    @Schema(
            description = "Identificador do perfil que criou a resenha",
            example = "2"
    )
    private Integer perfilId;


    @Schema(description = "Dados resumidos do livro associado à resenha")
    private LivroResumidoDTO livro;


    @Schema(
            description = "Título da resenha",
            example = "Uma Análise Profunda"
    )
    private String titulo;


    @Schema(
            description = "Nome do autor da resenha",
            example = "Maria Oliveira"
    )
    private String autor;


    @Schema(
            description = "Texto completo da resenha",
            example = "Esta é uma análise detalhada do livro..."
    )
    private String texto;


    @Schema(description = "Data de publicação da resenha",
            example = "2025-02-23"
    )
    private String dataPublicacao;


    @Schema(
            description = "Data da última edição da resenha",
            example = "2025-02-24"
    )
    private String dataEdicao;


    @Schema(
            description = "Nota atribuída à resenha",
            example = "4")
    private Double nota;


    @Schema(description = "Lista de avaliações associadas à resenha")
    private List<AvaliacaoDTO> avaliacoes;
}
