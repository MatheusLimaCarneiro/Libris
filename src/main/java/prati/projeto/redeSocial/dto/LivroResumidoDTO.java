package prati.projeto.redeSocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class LivroResumidoDTO {
    private String titulo;
    private String autor;
    private String dataPublicacao;
}
