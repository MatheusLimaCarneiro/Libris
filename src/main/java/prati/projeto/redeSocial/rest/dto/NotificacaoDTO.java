package prati.projeto.redeSocial.rest.dto;

import lombok.Data;

@Data
public class NotificacaoDTO {
    private Integer id;
    private String mensagem;
    private String tipo;
    private String  dataCriacao;
    private Integer remetenteId;
    private String remetenteUsername;
    private boolean lida;
}