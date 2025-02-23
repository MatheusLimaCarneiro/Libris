package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

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

    @OneToMany(mappedBy = "comentarioOriginal", cascade = CascadeType.ALL)
    private List<ComentarioResposta> respostas;
}
