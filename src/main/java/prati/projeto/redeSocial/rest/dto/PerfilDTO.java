package prati.projeto.redeSocial.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class PerfilDTO {
    private Integer id;
    private String urlPerfil;
    private String resumoBio;
    private Integer seguindo;
    private Integer seguidores;
    private String generosFavoritos;
    private String urlBackPerfil;
    private UsuarioResumidoDTO usuario;
}
