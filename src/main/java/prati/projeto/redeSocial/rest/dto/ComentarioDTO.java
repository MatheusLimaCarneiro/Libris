package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import io.swagger.v3.oas.annotations.media.Schema;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Data Transfer Object que representa um comentário.
 * <p>
 * Contém informações sobre o perfil que realizou o comentário, o livro associado,
 * o texto do comentário, a nota atribuída, a data em que o comentário foi realizado e
 * uma lista de respostas associadas ao comentário.
 * </p>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(description = "DTO para representação de um comentário")
public class ComentarioDTO {

    /**
     * Identificador único do comentário.
     */
    @Schema(description = "Identificador único do comentário", example = "1")
    private Integer id;

    /**
     * Identificador do perfil que realizou o comentário.
     */
    @NotNull(message = "Informe o ID do perfil")
    @Schema(
            description = "Identificador do perfil que realizou o comentário",
            example = "2"
    )
    private Integer perfilId;

    /**
     * Identificador do livro ao qual o comentário está associado.
     */
    @NotNull(message = "Informe o código do livro")
    @Schema(
            description = "Identificador do livro associado ao comentário",
            example = "10")
    private Integer livroId;

    /**
     * Texto do comentário.
     * <p>
     * Este campo não pode ser vazio e não pode conter apenas espaços.
     * </p>
     */
    @NotEmpty(message = "Campo de texto é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O texto não pode conter apenas espaços.")
    @Schema(
            description = "Texto do comentário",
            example = "Ótimo livro, recomendo a leitura!"
    )
    private String texto;


    @NotNull(message = "O comentário não pode ser realizado sem nota")
    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    @Schema(
            description = "Nota atribuída ao comentário",
            example = "4"
    )
    private Double nota;


    @Schema(
            description = "Data e hora em que o comentário foi realizado",
            example = "2025-02-23T14:30:00"
    )
    private LocalDateTime dataComentario;


    @Schema(description = "Lista de respostas associadas ao comentário")
    private List<RespostaDTO> respostas = new ArrayList<>();
}

