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
@Table(name = "relacionamentos_seguidores")
public class RelacionamentoSeguidores {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_seguidor", nullable = false)
    private Perfil seguidor;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "id_seguido", nullable = false)
    private Perfil seguido;

    @Column(name = "data_seguimento", nullable = false)
    private LocalDateTime dataSeguimento = LocalDateTime.now();
}
