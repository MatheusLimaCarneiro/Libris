package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;
import prati.projeto.redeSocial.modal.enums.GeneroLiterario;

import java.util.Set;

@Data
@NoArgsConstructor
@Schema(description = "Dados necessários para criar um perfil")
public class PerfilRequestDTO {

    @Schema(description = "URL da imagem do perfil", example = "https://exemplo.com/perfil.jpg")
    @URL(message = "A URL do perfil deve ser válida")
    private String urlPerfil;

    @Schema(description = "Resumo da biografia do perfil", example = "Amante de livros de fantasia e ficção")
    @NotEmpty(message = "O resumo da bio não pode estar vazio")
    private String resumoBio;

    @Schema(description = "Gêneros literários favoritos do usuário", example = "[\"FANTASIA\", \"FICCAO_CIENTIFICA\"]")
    @NotNull(message = "Os gêneros favoritos não podem ser nulos")
    @Size(min = 1, max = 3, message = "É necessário escolher entre 1 e 3 gêneros literários")
    private Set<GeneroLiterario> generosFavoritos;

    @Schema(description = "URL da imagem de fundo do perfil", example = "https://exemplo.com/capa.jpg")
    @URL(message = "A URL do fundo do perfil deve ser válida")
    private String urlBackPerfil;

    @Schema(description = "Email do usuário associado ao perfil", example = "usuario1@exemplo.com")
    @NotEmpty(message = "O email do usuário é obrigatório")
    private String usuarioEmail;
}