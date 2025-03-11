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
        name = "AvaliacaoDTO",
        description = "DTO que representa uma avaliação de uma resenha. " +
                "Contém informações como o ID do perfil que fez a avaliação, o texto da avaliação e a nota atribuída."
)
public class AvaliacaoDTO {

    @Schema(
            description = "ID da avaliação.",
            example = "1",
            accessMode = Schema.AccessMode.READ_ONLY
    )
    private Integer id;

    @Schema(
            description = "ID do perfil que fez a avaliação.",
            example = "1",
            required = true
    )
    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @Schema(
            description = "Texto da avaliação. Não pode ser vazio ou conter apenas espaços.",
            example = "Gostei muito da resenha!",
            required = true
    )
    @NotEmpty(message = "O texto da avaliação é obrigatório.")
    @Pattern(regexp = ".*\\S.*", message = "O texto da avaliação não pode conter apenas espaços.")
    private String texto;

    @Schema(
            description = "Nota da avaliação. Deve ser um valor entre 1 e 5.",
            example = "4.5",
            required = true
    )
    @NotNull(message = "A nota da avaliação é obrigatória.")
    @Min(value = 1, message = "A nota não pode ser menor que 1.")
    @Max(value = 5, message = "A nota não pode ser maior que 5.")
    private double nota;
}