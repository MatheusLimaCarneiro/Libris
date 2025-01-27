package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comentario_resposta")
public class ComentarioResposta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_comentario_resposta")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "comentario_id")
    private Comentario comentarioOriginal;

    @ManyToOne
    @JoinColumn(name = "perfil_id")
    private Perfil perfil;

    @Column(name = "texto_resposta", length = 500)
    private String texto;

    @Column(name = "data_resposta")
    private LocalDateTime dataResposta;
}
