package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resenha")
public class Resenha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_perfil")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "FK_livro")
    private Livro livro;

    private String titulo;
    private String autor;
    private String texto;

    @Column(name = "data_criacao")
    private LocalDateTime dataPublicacao;

    @Column(name = "data_alteracao")
    private LocalDateTime dataEdicao;
    private Double nota;

    @OneToMany(mappedBy = "resenha", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Avaliacao> avaliacoes = new ArrayList<>();
}
