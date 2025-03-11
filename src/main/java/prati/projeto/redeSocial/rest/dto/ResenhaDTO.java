package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(
        name = "ResenhaDTO",
        description = "DTO que representa uma resenha de livro no sistema. " +
                "Contém informações como o ID do perfil que fez a resenha, o Google ID do livro, " +
                "título da resenha, autor do livro, texto da resenha, nota atribuída e um indicador de spoiler."
)
public class ResenhaDTO {

    @Schema(
            description = "ID do perfil que fez a resenha.",
            example = "1"
    )
    @NotNull(message = "O ID do perfil é obrigatório")
    private Integer perfilId;

    @Schema(
            description = "Google ID do livro associado à resenha.",
            example = "1234567890"
    )
    @NotEmpty(message = "O Google ID do livro é obrigatório")
    private String googleId;

    @Schema(
            description = "Título da resenha (máximo de 100 caracteres).",
            example = "Uma análise profunda sobre o livro"
    )
    @NotEmpty(message = "O título é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O título não pode conter apenas espaços.")
    @Size(max = 100, message = "O título pode ter no máximo 100 caracteres")
    private String titulo;

    @Schema(
            description = "Nome do autor do livro (máximo de 100 caracteres).",
            example = "João Silva"
    )
    @NotEmpty(message = "O nome do autor é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O autor não pode conter apenas espaços.")
    @Size(max = 100, message = "O nome do autor pode ter no máximo 100 caracteres")
    private String autor;

    @Schema(
            description = "Texto da resenha.",
            example = "Este livro é incrível, recomendo a todos!"
    )
    @NotEmpty(message = "O texto da resenha é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O texto não pode conter apenas espaços.")
    private String texto;

    @Schema(
            description = "Nota atribuída ao livro (deve ser entre 1 e 5).",
            example = "4.5"
    )
    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Double nota;

    @Schema(
            description = "Indica se a resenha contém spoilers.",
            example = "true"
    )
    @NotNull(message = "A indicação de spoiler é obrigatória")
    private boolean spoiler;
}