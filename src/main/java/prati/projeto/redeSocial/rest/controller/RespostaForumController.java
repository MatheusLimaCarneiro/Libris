package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.RespostaForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.RespostaForumResponseDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.RespostaForumService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/comentarios/{comentarioForumId}/respostas-forum")
public class RespostaForumController {

    @Autowired
    private RespostaForumService respostaForumService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<RespostaForumResponseDTO> criarRespostaForum(
            @PathVariable Integer comentarioForumId,
            @RequestBody @Valid RespostaForumRequestDTO dto) {

        RespostaForumResponseDTO respostaDTO = respostaForumService.criarRespostaForum(comentarioForumId, dto);
        return new ServiceResponse<>(respostaDTO, "Resposta criada com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<RespostaForumResponseDTO>> listarRespostasPorComentario(
            @PathVariable Integer comentarioForumId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<RespostaForumResponseDTO> respostasPage = respostaForumService.listarPorComentario(comentarioForumId, page, size);
        String mensagem = respostasPage.isEmpty() ? "Nenhuma resposta encontrada" : "Respostas encontradas";
        return new ServiceResponse<>(respostasPage, mensagem, !respostasPage.isEmpty(), getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarRespostaForum(
            @PathVariable Integer comentarioForumId,
            @PathVariable Integer id) {
        respostaForumService.deletarRespostaForum(comentarioForumId, id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<RespostaForumResponseDTO> buscarRespostaPorId(
            @PathVariable Integer comentarioForumId, @PathVariable Integer id) {

        RespostaForumResponseDTO respostaDTO = respostaForumService.buscarRespostaForum(comentarioForumId, id);
        return new ServiceResponse<>(respostaDTO, "Resposta encontrada", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}