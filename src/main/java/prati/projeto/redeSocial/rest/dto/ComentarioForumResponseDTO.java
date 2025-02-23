package prati.projeto.redeSocial.rest.dto;

import lombok.Data;
import java.time.LocalDateTime;
import java.util.List;

@Data
public class ComentarioForumResponseDTO {
    private Integer id;
    private String texto;
    private String nomePerfil;
    private LocalDateTime data;
    private List<RespostaForumResponseDTO> respostas;
}