package prati.projeto.redeSocial.rest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class RespostaForumResponseDTO {
    private Integer id;
    private String texto;
    private String nomePerfil;
    private Integer quantidadeCurtidas;
    private LocalDateTime data;
}
