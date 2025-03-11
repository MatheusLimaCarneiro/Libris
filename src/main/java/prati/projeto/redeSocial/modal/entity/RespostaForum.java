package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "resposta_forum")
public class RespostaForum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "comentario_forum_id")
    private ComentarioForum comentarioForum;

    @Column(length = 1000)
    private String texto;

    @Column(name = "quantidade_curtidas")
    private Integer quantidadeCurtidas = 0;

    @ElementCollection
    @CollectionTable(name = "curtidas_resposta_forum", joinColumns = @JoinColumn(name = "resposta_id"))
    @Column(name = "perfil_id")
    private Set<Integer> perfisQueCurtiram = new HashSet<>();

    @Column(name = "data_resposta")
    private LocalDateTime data;
}