package prati.projeto.redeSocial.rest.dto;

import lombok.Data;
import lombok.NoArgsConstructor;
import prati.projeto.redeSocial.modal.enums.GeneroLiterario;

import java.util.Set;

@Data
@NoArgsConstructor
public class PerfilDTO {
    private Integer id;
    private String urlPerfil;
    private String resumoBio;
    private Integer seguindo;
    private Integer seguidores;
    private Set<GeneroLiterario> generosFavoritos;
    private String urlBackPerfil;
    private UsuarioResumidoDTO usuario;
}
