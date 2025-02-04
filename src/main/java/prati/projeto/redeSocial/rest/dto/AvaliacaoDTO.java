package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AvaliacaoDTO {

    private Integer id;

    @NotNull(message = "O ID do perfil é obrigatório.")
    private Integer perfilId;

    @NotEmpty(message = "O texto da avaliação é obrigatório.")
    @Pattern(regexp = ".*\\S.*", message = "O texto da avaliação não pode conter apenas espaços.")
    private String texto;

    @NotNull(message = "A nota da avaliação é obrigatória.")
    @Min(value = 1, message = "A nota não pode ser menor que 1.")
    @Max(value = 5, message = "A nota não pode ser maior que 5.")
    private double nota;
}
