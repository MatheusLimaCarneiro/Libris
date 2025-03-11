package prati.projeto.redeSocial.rest.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(
        name = "NotificacaoDTO",
        description = "DTO que representa uma notificação no sistema. " +
                "Contém informações como o ID da notificação, a mensagem, o tipo, a data de criação, " +
                "o ID e username do remetente, e um indicador se a notificação foi lida."
)
public class NotificacaoDTO {

    @Schema(
            description = "ID único da notificação.",
            example = "1"
    )
    private Integer id;

    @Schema(
            description = "Mensagem da notificação.",
            example = "Você recebeu uma nova mensagem."
    )
    private String mensagem;

    @Schema(
            description = "Tipo da notificação (ex: mensagem, curtida, comentário).",
            example = "mensagem"
    )
    private String tipo;

    @Schema(
            description = "Data de criação da notificação no formato 'yyyy-MM-dd HH:mm:ss'.",
            example = "2023-10-15 14:30:00"
    )
    private String dataCriacao;

    @Schema(
            description = "ID do remetente da notificação.",
            example = "2"
    )
    private Integer remetenteId;

    @Schema(
            description = "Username do remetente da notificação.",
            example = "joao_silva"
    )
    private String remetenteUsername;

    @Schema(
            description = "Indica se a notificação foi lida.",
            example = "false"
    )
    private boolean lida;
}