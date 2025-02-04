package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.RespostaDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.ComentarioRespostaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/libris/comentarios/{comentarioId}/respostas")
public class ComentarioRespostaController {

    @Autowired
    private ComentarioRespostaService respostaService;


    public ComentarioRespostaController(ComentarioRespostaService respostaService) {
        this.respostaService = respostaService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RespostaDTO> adicionarResposta(
            @PathVariable Integer comentarioId,
            @RequestBody @Valid RespostaDTO respostaDTO) {
        RespostaDTO resposta = respostaService.adicionarResposta(comentarioId, respostaDTO);
        return new ApiResponse<>(resposta, "Resposta adicionada com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<RespostaDTO>> listarRespostas(@PathVariable Integer comentarioId) {
        List<RespostaDTO> respostas = respostaService.listarRespostasPorComentario(comentarioId);
        return new ApiResponse<>(respostas, "Respostas listadas com sucesso", true, getFormattedTimestamp());
    }

    @PutMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<RespostaDTO> atualizarResposta(
            @PathVariable Integer respostaId,
            @RequestBody @Valid RespostaDTO respostaDTO) {
        RespostaDTO respostaAtualizada = respostaService.atualizarResposta(respostaId, respostaDTO);
        return new ApiResponse<>(respostaAtualizada, "Resposta atualizada com sucesso", true, getFormattedTimestamp());
    }

    @DeleteMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarResposta(@PathVariable Integer respostaId) {
        respostaService.deletarResposta(respostaId);
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
