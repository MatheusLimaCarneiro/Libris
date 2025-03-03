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
@Table(name = "notificacao")
public class Notificacao {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "perfil_destinatario_id", nullable = false)
    private Perfil destinatario;

    @ManyToOne
    @JoinColumn(name = "perfil_remetente_id", nullable = false)
    private Perfil remetente;

    private String mensagem;
    private boolean lida = false;
    private LocalDateTime dataCriacao = LocalDateTime.now();

    private String tipo;
}
