package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.URL;
import prati.projeto.redeSocial.modal.enums.GeneroLiterario;

import java.util.Objects;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_perfil")
    private Integer id;

    @Column(name = "url_perfil", length = 500)
    @URL(message = "A URL do perfil deve ser válida")
    private String urlPerfil;

    @Column(name = "resumo_bio", length = 120)
    @NotEmpty(message = "O resumo da bio não pode estar vazio")
    @Size(max = 120, message = "A bio deve ter no máximo 120 caracteres")
    private String resumoBio;

    @Column(name = "seguindo")
    private Integer seguindo = 0;

    @Column(name = "seguidores")
    private Integer seguidores = 0;

    @ElementCollection(targetClass = GeneroLiterario.class)
    @CollectionTable(
            name = "perfil_generos",
            joinColumns = @JoinColumn(name = "PK_perfil")
    )
    @Enumerated(EnumType.STRING)
    @Column(name = "generos_Favoritos", length = 50)
    @Size(min = 1, max = 3, message = "É necessario escolher entre 1 e 3 gêneros literários")
    private Set<GeneroLiterario> generosFavoritos;

    @Column(name = "url_back_perfil", length = 500)
    @URL(message = "A URL do fundo do perfil deve ser válida")
    private String urlBackPerfil;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "FK_usuario", referencedColumnName = "email")
    private Usuario usuario;

    @OneToMany(mappedBy = "seguidor")
    private Set<RelacionamentoSeguidores> relacionamentosSeguindo;

    @OneToMany(mappedBy = "seguido")
    private Set<RelacionamentoSeguidores> relacionamentosSeguidores;

}
