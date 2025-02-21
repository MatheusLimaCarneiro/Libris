package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.CurtidaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequiredArgsConstructor
@RequestMapping("libris/curtidas")
public class CurtidaController {

    private final CurtidaService curtidaService;

    @PostMapping("/comentario/{comentarioId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Void> curtirComentario(
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @PathVariable @Positive(message = "ID do comentário deve ser positivo") Integer comentarioId) {
        curtidaService.curtirComentario(perfilId, comentarioId);
        return new ServiceResponse<>(null, "Comentário curtido com sucesso", true, getFormattedTimestamp());
    }

    @DeleteMapping("/comentario/{comentarioId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> descurtirComentario(
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @PathVariable @Positive(message = "ID do comentário deve ser positivo") Integer comentarioId) {
        curtidaService.descurtirComentario(perfilId, comentarioId);
        return new ServiceResponse<>(null, "Comentário descurtido com sucesso", true, getFormattedTimestamp());
    }

    @PostMapping("/resposta/{respostaId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Void> curtirResposta(
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @PathVariable @Positive(message = "ID da resposta deve ser positivo") Integer respostaId) {
        curtidaService.curtirResposta(perfilId, respostaId);
        return new ServiceResponse<>(null, "Resposta curtida com sucesso", true, getFormattedTimestamp());
    }

    @DeleteMapping("/resposta/{respostaId}/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ServiceResponse<Void> descurtirResposta(
            @PathVariable @Positive(message = "ID do perfil deve ser positivo") Integer perfilId,
            @PathVariable @Positive(message = "ID da resposta deve ser positivo") Integer respostaId) {
        curtidaService.descurtirResposta(perfilId, respostaId);
        return new ServiceResponse<>(null, "Resposta descurtida com sucesso", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}