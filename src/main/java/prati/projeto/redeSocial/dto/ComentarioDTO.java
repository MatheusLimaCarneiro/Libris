package prati.projeto.redeSocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ComentarioDTO {
    private Integer id;
    private Integer usuarioId;
    private Integer livroId;
    private String texto;
    private AvaliacaoDTO avaliacao;
    private LocalDateTime dataComentario;
}
