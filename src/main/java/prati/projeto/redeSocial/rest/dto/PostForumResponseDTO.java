package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Schema(
        name = "PostForumResponseDTO",
        description = "DTO que representa a resposta de um post no fórum. " +
                "Contém informações como o ID do post, texto, tags, indicação de spoiler, nome do perfil, título do livro, " +
                "data de criação, quantidade de curtidas e lista de comentários."
)
public class PostForumResponseDTO {

    @Schema(
            description = "ID único do post.",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Texto do post.",
            example = "Este livro mudou minha vida!"
    )
    private String texto;

    @Schema(
            description = "Tags associadas ao post.",
            example = "ficção,aventura"
    )
    private String tags;

    @Schema(
            description = "Indica se o post contém spoiler.",
            example = "true"
    )
    private Boolean possuiSpoiler;

    @Schema(
            description = "Nome do perfil que criou o post.",
            example = "joao_silva"
    )
    private String nomePerfil;

    @Schema(
            description = "Título do livro associado ao post.",
            example = "Clean Code"
    )
    private String tituloLivro;

    @Schema(
            description = "Data de criação do post no formato 'dd/MM/yyyy HH:mm'.",
            example = "15/10/2023 14:30"
    )
    private String dataCriacao;

    @Schema(
            description = "Quantidade de curtidas que o post recebeu.",
            example = "10"
    )
    private Integer curtidas;

    @Schema(
            description = "Lista de comentários associados ao post.",
            implementation = ComentarioForumResponseDTO.class
    )
    private List<ComentarioForumResponseDTO> comentarios;
}