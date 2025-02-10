package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.NoArgsConstructor;
import prati.projeto.redeSocial.modal.enums.GeneroLiterario;

import java.util.Set;

@Data
@NoArgsConstructor
@Schema(description = "Objeto que representa os dados completos de um perfil de usuário")
public class PerfilDTO {

    @Schema(description = "ID do perfil", example = "1")
    private Integer id;

    @Schema(description = "URL da imagem do perfil", example = "https://exemplo.com/perfil.jpg")
    private String urlPerfil;

    @Schema(description = "Resumo da biografia do perfil", example = "Amo ler livros de fantasia e ficção científica.")
    private String resumoBio;

    @Schema(description = "Quantidade de usuários que este perfil está seguindo", example = "150")
    private Integer seguindo;

    @Schema(description = "Quantidade de seguidores deste perfil", example = "200")
    private Integer seguidores;

    @Schema(description = "Gêneros literários favoritos do usuário")
    private Set<GeneroLiterario> generosFavoritos;

    @Schema(description = "URL da imagem de fundo do perfil", example = "https://exemplo.com/fundo.jpg")
    private String urlBackPerfil;

    @Schema(description = "Informações resumidas do usuário vinculado a este perfil")
    private UsuarioResumidoDTO usuario;
}
