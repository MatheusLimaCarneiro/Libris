package prati.projeto.redeSocial.rest.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {

    private Integer id;

    @NotNull(message = "Informe o código do usuario")
    private Integer usuarioId;

    @NotNull(message = "Informe o código do livro")
    private Integer livroId;

    @NotEmpty(message = "Campo de texto é obrigatório")
    private String texto;

    @Valid
    private AvaliacaoDTO avaliacao;

    private LocalDateTime dataComentario;
}
