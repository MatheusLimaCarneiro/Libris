package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResenhaDTO {

    @NotNull(message = " O ID do perfil é obrigatório")
    private Integer perfilId;

    @NotNull(message = "O ID do livro é obrigatório")
    private Integer livroId;

    @NotEmpty(message = "O título é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O título não pode conter apenas espaços.")
    @Size(max = 100, message = "O título pode ter no máximo 100 caracteres")
    private String titulo;

    @NotEmpty(message = "O nome do autor é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O autor não pode conter apenas espaços.")
    @Size(max = 100, message = "O nome do autor pode ter no máximo 100 caracteres")
    private String autor;

    @NotEmpty(message = "O texto da resenha é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O Texto não pode conter apenas espaços.")
    private String texto;

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Double nota;
}
