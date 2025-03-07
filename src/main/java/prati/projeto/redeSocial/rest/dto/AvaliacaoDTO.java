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
        description = "Classe de transferência de dados referente a avaliação."
)
public class AvaliacaoDTO {

    @Schema(
            description = "Identificador único da avaliação",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Identificador único do perfil que está sendo avaliado",
            example = "123"
    )
    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @Schema(
            description = "Texto da avaliação",
            example = "Ótimo perfil, recomendo!"
    )
    @NotEmpty(message = "O texto da avaliação é obrigatório.")
    @Pattern(regexp = ".*\\S.*", message = "O texto da avaliação não pode conter apenas espaços.")
    private String texto;

    @Schema(
            description = "Nota da avaliação (deve estar entre 1 e 5)",
            example = "4"
    )
    @NotNull(message = "A nota da avaliação é obrigatória.")
    @Min(value = 1, message = "A nota não pode ser menor que 1.")
    @Max(value = 5, message = "A nota não pode ser maior que 5.")
    private double nota;
}