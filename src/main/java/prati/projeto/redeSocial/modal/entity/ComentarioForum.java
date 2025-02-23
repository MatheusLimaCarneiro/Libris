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
@Table(name = "comentario_forum")
public class ComentarioForum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 1000)
    private String texto;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "post_forum_id")
    private PostForum postForum;

    @Column(name = "data_comentario")
    private LocalDateTime data = LocalDateTime.now();

    @Column(name = "quantidade_curtidas")
    private Integer quantidadeCurtidas = 0;

    @ElementCollection
    @CollectionTable(name = "curtidas_comentario_forum", joinColumns = @JoinColumn(name = "comentario_id"))
    @Column(name = "perfil_id")
    private Set<Integer> perfisQueCurtiram = new HashSet<>();

    @OneToMany(mappedBy = "comentarioForum", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<RespostaForum> respostas = new ArrayList<>();}