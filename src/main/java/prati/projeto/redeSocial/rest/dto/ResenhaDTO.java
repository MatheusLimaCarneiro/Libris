package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResenhaDTO {

    @NotEmpty(message = "Email do usuário é obrigatório")
    @Email(message = "Email inválido")
    private String usuarioEmail;

    @NotNull(message = "O ID do livro é obrigatório")
    private Integer livroId;

    @NotEmpty(message = "O título é obrigatório")
    @Size(max = 100, message = "O título pode ter no máximo 100 caracteres")
    private String titulo;

    @NotEmpty(message = "O nome do autor é obrigatório")
    @Size(max = 100, message = "O nome do autor pode ter no máximo 100 caracteres")
    private String autor;

    @NotNull(message = "A nota é obrigatória")
    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Double nota;
}
