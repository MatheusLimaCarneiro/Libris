package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.*;
import lombok.Data;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.Parameter;

@Data
@Schema(description = "DTO para criação ou atualização de um comentário")
public class ComentarioRequestDTO {

    @NotNull(message = "Informe o ID do perfil")
    @Parameter(description = "ID do perfil que está fazendo o comentário", required = true, example = "1")
    @Schema(description = "ID do perfil que está fazendo o comentário", example = "1")
    private Integer perfilId;

    @NotNull(message = "Informe o código do livro")
    @Parameter(description = "Google ID do livro associado ao comentário", required = true, example = "XYZ123")
    @Schema(description = "Google ID do livro associado ao comentário", example = "XYZ123")
    private String googleId;

    @NotEmpty(message = "Campo de texto é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O texto não pode conter apenas espaços.")
    @Parameter(description = "Texto do comentário", required = true, example = "Este livro é incrível!")
    @Schema(description = "Texto do comentário", example = "Este livro é incrível!")
    private String texto;

    @NotNull(message = "O comentário não pode ser realizado sem nota")
    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    @Parameter(description = "Nota atribuída ao livro (deve estar entre 1 e 5)", required = true, example = "4.5")
    @Schema(description = "Nota atribuída ao livro (deve estar entre 1 e 5)", example = "4.5")
    private Double nota;

    @NotNull(message = "A nota é obrigatória")
    @Parameter(description = "Indica se o comentário contém spoiler", required = true, example = "false")
    @Schema(description = "Indica se o comentário contém spoiler", example = "false")
    private boolean spoiler;
}