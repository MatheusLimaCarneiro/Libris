package prati.projeto.redeSocial.modal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import prati.projeto.redeSocial.modal.enums.GeneroLiterario;

import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Schema(description = "Entidade que representa o perfil de um usuário na rede social.")
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_perfil")
    @Schema(description = "Identificador único do perfil", example = "1")
    private Integer id;

    @Column(name = "url_perfil", length = 500)
    @URL(message = "A URL do perfil deve ser válida")
    @Schema(
            description = "URL da imagem de perfil do usuário",
            example = "https://example.com/profile.jpg"
    )
    private String urlPerfil;

    @Column(name = "resumo_bio", length = 120)
    @NotEmpty(message = "O resumo da bio não pode estar vazio")
    @Size(max = 120, message = "A bio deve ter no máximo 120 caracteres")
    @Schema(
            description = "Resumo da bio do usuário, até 120 caracteres",
            example = "Leitor ávido de ficção científica."
    )
    private String resumoBio;

    @Column(name = "seguindo")
    @Schema(description = "Número de usuários que o perfil está seguindo", example = "150")
    private Integer seguindo = 0;

    @Column(name = "seguidores")
    @Schema(description = "Número de seguidores do perfil", example = "250")
    private Integer seguidores = 0;

    @ElementCollection(targetClass = GeneroLiterario.class)
    @CollectionTable(
            name = "perfil_generos",
            joinColumns = @JoinColumn(name = "PK_perfil")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "generos_Favoritos", length = 50)
    @Size(min = 1, max = 3, message = "É necessário escolher entre 1 e 3 gêneros literários")
    @Schema(
            description = "Gêneros literários favoritos do usuário",
            example = "[\"Ficção Científica\", \"Fantasia\"]"
    )
    private Set<GeneroLiterario> generosFavoritos;

    @Column(name = "url_back_perfil", length = 500)
    @URL(message = "A URL do fundo do perfil deve ser válida")
    @Schema(
            description = "URL da imagem de fundo do perfil",
            example = "https://example.com/background.jpg"
    )
    private String urlBackPerfil;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_usuario", referencedColumnName = "email")
    @Schema(
            description = "Usuário associado ao perfil",
            example = "usuario@example.com"
    )
    private Usuario usuario;

    @OneToMany(mappedBy = "seguidor")
    @Schema(description = "Relacionamentos de seguidores do perfil")
    private Set<RelacionamentoSeguidores> relacionamentosSeguindo;

    @OneToMany(mappedBy = "seguido")
    @Schema(description = "Relacionamentos de seguidores do perfil")
    private Set<RelacionamentoSeguidores> relacionamentosSeguidores;
}
