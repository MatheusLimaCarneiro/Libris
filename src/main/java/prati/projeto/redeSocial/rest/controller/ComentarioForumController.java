package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.ComentarioForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.ComentarioForumResponseDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.ComentarioForumService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/posts/{postId}/comentarios")
public class ComentarioForumController {

    @Autowired
    private ComentarioForumService comentarioForumService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<ComentarioForumResponseDTO> criarComentario(
            @PathVariable Integer postId,
            @RequestBody @Valid ComentarioForumRequestDTO dto) {

        ComentarioForumResponseDTO comentarioDTO = comentarioForumService.criarComentario(postId, dto);
        return new ServiceResponse<>(comentarioDTO, "Comentário criado com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ComentarioForumResponseDTO>> listarComentariosPorPost(
            @PathVariable Integer postId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ComentarioForumResponseDTO> comentariosPage = comentarioForumService.listarPorPost(postId, page, size);
        String mensagem = comentariosPage.isEmpty() ? "Nenhum comentário encontrado" : "Comentários encontrados";
        return new ServiceResponse<>(comentariosPage, mensagem, !comentariosPage.isEmpty(), getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarComentario(
            @PathVariable Integer postId,@PathVariable Integer id) {
        comentarioForumService.deletarComentario(postId, id);
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<ComentarioForumResponseDTO> buscarComentarioPorId(
            @PathVariable Integer postId, @PathVariable Integer id) {

        ComentarioForumResponseDTO comentarioDTO = comentarioForumService.buscarComentario(postId, id);
        return new ServiceResponse<>(comentarioDTO, "Comentário encontrado", true, getFormattedTimestamp());
    }

    @GetMapping("/usuario/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ComentarioForumResponseDTO>> listarComentariosPorUsername(
            @PathVariable Integer postId,
            @PathVariable String username,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ComentarioForumResponseDTO> comentariosPage = comentarioForumService.listarComentariosPorUsername(username, postId, page, size);
        String mensagem = comentariosPage.isEmpty() ? "Nenhum comentário encontrado para este usuário" : "Comentários encontrados para este usuário";
        return new ServiceResponse<>(comentariosPage, mensagem, !comentariosPage.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
