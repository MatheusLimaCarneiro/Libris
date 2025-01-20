package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Perfil {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_perfil")
    private Integer id;

    @Column(name = "url_perfil", length = 500)
    private String urlPerfil;

    @Column(name = "resumo_bio", length = 120)
    private String resumoBio;

    @Column(name = "seguindo")
    private Integer seguindo = 0;

    @Column(name = "seguidores")
    private Integer seguidores = 0;

    @Column(name = "generos_Favoritos", length = 50)
    private String generosFavoritos;

    @Column(name = "url_back_perfil", length = 500)
    private String urlBackPerfil;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "usuario_email", referencedColumnName = "email")
    private Usuario usuario;
}
