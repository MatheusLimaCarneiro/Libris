package prati.projeto.redeSocial.rest.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class NotificacaoDTO {
    private Integer id;
    private String mensagem;
    private String tipo;
    private LocalDateTime dataCriacao;
    private Integer remetenteId;
    private String remetenteUsername;
    private boolean lida;
}