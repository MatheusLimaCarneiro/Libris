package prati.projeto.redeSocial.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ResenhaViewDTO {
    private Integer id;
    private Integer perfilId;
    private LivroResumidoDTO livro;
    private String titulo;
    private String autor;
    private String texto;
    private String dataPublicacao;
    private String dataEdicao;
    private Double nota;
    private List<AvaliacaoDTO> avaliacoes;
}
