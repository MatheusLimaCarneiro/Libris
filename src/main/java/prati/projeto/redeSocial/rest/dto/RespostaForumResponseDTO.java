package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "RespostaForumResponseDTO",
        description = "DTO que representa a resposta de uma resposta no fórum. " +
                "Contém informações como o ID da resposta, o texto, o nome do perfil, a quantidade de curtidas e a data de criação."
)
public class RespostaForumResponseDTO {

    @Schema(
            description = "ID único da resposta.",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Texto da resposta.",
            example = "Concordo com o seu ponto de vista!"
    )
    private String texto;

    @Schema(
            description = "Nome do perfil que criou a resposta.",
            example = "joao_silva"
    )
    private String nomePerfil;

    @Schema(
            description = "Quantidade de curtidas que a resposta recebeu.",
            example = "10"
    )
    private Integer quantidadeCurtidas;

    @Schema(
            description = "Data de criação da resposta no formato 'dd/MM/yyyy HH:mm'.",
            example = "15/10/2023 14:30"
    )
    private String data;
}