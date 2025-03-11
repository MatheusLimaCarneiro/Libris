package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "post_forum")
public class PostForum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1000)
    private String texto;

    @Column(length = 200)
    private String tags;

    @Column(name = "possui_spoiler")
    private Boolean possuiSpoiler;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    @Column(name = "google_id_livro")
    private String googleIdLivro;

    @Column(name = "data_post")
    private LocalDateTime dataCriacao = LocalDateTime.now();

    @OneToMany(mappedBy = "postForum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ComentarioForum> comentarios;

    @Column(name = "quantidade_curtidas")
    private Integer quantidadeCurtidas = 0;

    @ElementCollection
    @CollectionTable(name = "curtidas_post_forum", joinColumns = @JoinColumn(name = "post_id"))
    @Column(name = "perfil_id")
    private Set<Integer> perfisQueCurtiram = new HashSet<>();

}