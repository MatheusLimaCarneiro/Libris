package prati.projeto.redeSocial.rest.dto;

import lombok.Getter;
import lombok.Setter;
import java.time.LocalDateTime;

@Getter
@Setter
public class PostForumResponseDTO {
    private Integer id;
    private String texto;
    private String tags;
    private Boolean possuiSpoiler;
    private String nomePerfil;
    private String tituloLivro;
    private LocalDateTime dataCriacao;
}
