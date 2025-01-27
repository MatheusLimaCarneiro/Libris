package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "avaliacao")
public class Avaliacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "PK_avaliacao")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_resenha")
    private Resenha resenha;

    @ManyToOne
    @JoinColumn(name = "FK_perfil")
    private Perfil perfil;

    private String texto;

    private double nota;
}
