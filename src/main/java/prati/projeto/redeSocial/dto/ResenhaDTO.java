package prati.projeto.redeSocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResenhaDTO {
    private Integer usuarioId;
    private Integer livroId;
    private String titulo;
    private String autor;
    private Integer nota;
}
