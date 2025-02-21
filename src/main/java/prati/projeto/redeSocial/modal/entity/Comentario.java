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
@Table(name = "comentario_livro")
public class Comentario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_perfil")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "livro_id")
    private Livro livro;

    private String texto;

    private Double nota;

    private LocalDateTime dataComentario;

    @ElementCollection
    @CollectionTable(name = "curtidas_comentario", joinColumns = @JoinColumn(name = "comentario_id"))
    @Column(name = "perfil_id")
    private Set<Integer> perfisQueCurtiram = new HashSet<>();

    @Column(name = "quantidade_curtidas")
    private Integer quantidadeCurtidas = 0;

    @OneToMany(mappedBy = "comentarioOriginal", cascade = CascadeType.ALL)
    private List<ComentarioResposta> respostas;
}
