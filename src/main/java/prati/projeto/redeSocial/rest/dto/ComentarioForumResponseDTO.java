package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.util.List;

@Data
@Schema(
        name = "ComentarioForumResponseDTO",
        description = "DTO que representa a resposta de um comentário no fórum. " +
                "Contém informações como o ID do comentário, o texto, o nome do perfil, a data de criação, " +
                "a quantidade de curtidas, a lista de respostas e uma indicação de spoiler."
)
public class ComentarioForumResponseDTO {

    @Schema(
            description = "ID único do comentário.",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Texto do comentário.",
            example = "Este post é incrível!"
    )
    private String texto;

    @Schema(
            description = "Nome do perfil que criou o comentário.",
            example = "joao_silva"
    )
    private String nomePerfil;

    @Schema(
            description = "Data de criação do comentário no formato 'dd/MM/yyyy HH:mm'.",
            example = "15/10/2023 14:30"
    )
    private String data;

    @Schema(
            description = "Quantidade de curtidas que o comentário recebeu.",
            example = "10"
    )
    private Integer quantidadeCurtidas;

    @Schema(
            description = "Lista de respostas associadas ao comentário.",
            implementation = RespostaForumResponseDTO.class
    )
    private List<RespostaForumResponseDTO> respostas;

    @Schema(
            description = "Indica se o comentário contém spoiler.",
            example = "true"
    )
    private boolean spoiler;
}