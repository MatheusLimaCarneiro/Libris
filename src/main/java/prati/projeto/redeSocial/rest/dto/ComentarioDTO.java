package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representar um comentário na resposta")
public class ComentarioDTO {

    @Schema(description = "ID do comentário", example = "1")
    private Integer id;

    @Schema(description = "ID do perfil que fez o comentário", example = "1")
    private Integer perfilId;

    @Schema(description = "Google ID do livro associado ao comentário", example = "XYZ123")
    private String googleId;

    @Schema(description = "Texto do comentário", example = "BLA BLA BLA")
    private String texto;

    @Schema(description = "Nota atribuída ao livro (deve estar entre 1 e 5)", example = "4.0")
    private Double nota;

    @Schema(description = "Data e hora do comentário", example = "07/03/2025 14:59")
    private String dataComentario;

    @Schema(description = "Quantidade de curtidas do comentário", example = "0")
    private Integer quantidadeCurtidas;

    @Schema(description = "Indica se o comentário contém spoiler", example = "false")
    private boolean spoiler;
    private List<RespostaDTO> respostas = new ArrayList<>();

}
