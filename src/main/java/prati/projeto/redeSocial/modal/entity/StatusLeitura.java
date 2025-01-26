package prati.projeto.redeSocial.modal.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import prati.projeto.redeSocial.modal.enums.StatusLeituraEnum;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tb_statusLeitura")
public class StatusLeitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_perfil", nullable = false)
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "FK_livro", nullable = false)
    private Livro livro;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusLeituraEnum statusLeitura;
}
