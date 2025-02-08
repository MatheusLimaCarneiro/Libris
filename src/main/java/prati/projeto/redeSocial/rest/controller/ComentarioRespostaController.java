package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.RespostaDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.ComentarioRespostaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/comentarios/{comentarioId}/respostas")
public class ComentarioRespostaController {

    @Autowired
    private ComentarioRespostaService respostaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<RespostaDTO> adicionarResposta(
            @PathVariable Integer comentarioId,
            @RequestBody @Valid RespostaDTO respostaDTO) {
        RespostaDTO resposta = respostaService.adicionarResposta(comentarioId, respostaDTO);
        return new ApiResponse<>(resposta, "Resposta adicionada com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<RespostaDTO> buscarRespostaPorId(
            @PathVariable Integer comentarioId,
            @PathVariable Integer respostaId) {
        RespostaDTO resposta = respostaService.buscarRespostaPorId(comentarioId, respostaId);
        return new ApiResponse<>(resposta, "Resposta encontrada com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<RespostaDTO>> listarRespostas(
            @PathVariable Integer comentarioId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<RespostaDTO> respostas = respostaService.listarRespostasPorComentario(comentarioId, page, size);
        String mensagem = respostas.isEmpty() ? "Nenhuma resposta encontrada" : "Respostas encontradas";
        return new ApiResponse<>(respostas, mensagem, !respostas.isEmpty(), getFormattedTimestamp());
    }

    @PutMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<RespostaDTO> atualizarResposta(
            @PathVariable Integer comentarioId,
            @PathVariable Integer respostaId,
            @RequestBody @Valid RespostaDTO respostaDTO) {
        RespostaDTO respostaAtualizada = respostaService.atualizarResposta(comentarioId, respostaId, respostaDTO);
        return new ApiResponse<>(respostaAtualizada, "Resposta atualizada com sucesso", true, getFormattedTimestamp());
    }

    @DeleteMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarResposta(
            @PathVariable Integer comentarioId,
            @PathVariable Integer respostaId) {
        respostaService.deletarResposta(comentarioId, respostaId);
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
