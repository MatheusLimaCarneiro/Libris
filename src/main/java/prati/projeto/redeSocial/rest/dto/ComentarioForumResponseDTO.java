package prati.projeto.redeSocial.rest.dto;

import lombok.Data;
import java.util.List;

@Data
public class ComentarioForumResponseDTO {
    private Integer id;
    private String texto;
    private String nomePerfil;
    private String data;
    private Integer quantidadeCurtidas;
    private List<RespostaForumResponseDTO> respostas;
    private boolean spoiler;
}