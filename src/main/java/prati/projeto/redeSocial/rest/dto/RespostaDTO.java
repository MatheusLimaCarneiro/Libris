package prati.projeto.redeSocial.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RespostaDTO {
    private Integer id;
    private String usuarioEmail;
    private String texto;
    private LocalDateTime dataResposta;
}
