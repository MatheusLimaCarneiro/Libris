package prati.projeto.redeSocial.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ResenhaViewDTO {
    private Integer id;
    private UsuarioResumidoDTO usuario;
    private LivroResumidoDTO livro;
    private String titulo;
    private String autor;
    private String dataPublicacao;
    private String dataEdicao;
    private Integer nota;
}
