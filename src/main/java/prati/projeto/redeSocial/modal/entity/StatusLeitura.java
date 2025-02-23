package prati.projeto.redeSocial.modal.entity;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Entidade que representa o status de leitura de um livro por um usuário.")
public class StatusLeitura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Schema(description = "ID único do status de leitura", example = "1")
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "FK_perfil", nullable = false)
    @Schema(description = "Perfil do usuário associado ao status de leitura")
    private Perfil perfil;

    @ManyToOne
    @JoinColumn(name = "FK_livro", nullable = false)
    @Schema(description = "Livro associado ao status de leitura")
    private Livro livro;

    @Schema(description = "Número da página que o usuário parou de ler", example = "150")
    private Integer pagina;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    @Schema(description = "Status da leitura do livro", example = "EM_PROGRESSO")
    private StatusLeituraEnum statusLeitura;
}
