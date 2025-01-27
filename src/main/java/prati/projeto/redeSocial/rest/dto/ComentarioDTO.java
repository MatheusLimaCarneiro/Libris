package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {

    private Integer id;

    @NotNull(message = "Informe o ID do perfil")
    private Integer perfilId;

    @NotNull(message = "Informe o código do livro")
    private Integer livroId;

    @NotEmpty(message = "Campo de texto é obrigatório")
    @Pattern(regexp = ".*\\S.*", message = "O texto não pode conter apenas espaços.")
    private String texto;

    @NotNull(message = "O comentário não pode ser realizado sem nota")
    @Min(value = 1, message = "A nota deve ser no mínimo 1")
    @Max(value = 5, message = "A nota deve ser no máximo 5")
    private Double nota;

    private LocalDateTime dataComentario;

    private List<RespostaDTO> respostas = new ArrayList<>();
}
