package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.ComentarioDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.ComentarioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;


@RestController
@RequestMapping("/libris/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<ComentarioDTO> saveComentario(@RequestBody @Valid ComentarioDTO dto) {
        ComentarioDTO comentarioDTO = comentarioService.salvar(dto);
        return new ServiceResponse<>(comentarioDTO, "Comentário criado com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<ComentarioDTO> buscarPorId(@PathVariable Integer id) {
        ComentarioDTO comentarioDTO = comentarioService.buscarPorId(id);
        return new ServiceResponse<>(comentarioDTO, "Comentário encontrado", true, getFormattedTimestamp());
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ComentarioDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ComentarioDTO> comentarios = comentarioService.listarTodos(page, size);
        String mensagem = comentarios.isEmpty() ? "Nenhum comentário encontrado" : "Comentários encontrados";
        return new ServiceResponse<>(comentarios, mensagem, !comentarios.isEmpty(), getFormattedTimestamp());
    }


    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<ComentarioDTO> atualizarComentario(@PathVariable Integer id,
                                                              @RequestBody @Valid ComentarioDTO dto) {
        ComentarioDTO comentarioDTO = comentarioService.atualizarComentario(id, dto);
        return new ServiceResponse<>(comentarioDTO, "Comentário atualizado com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/listar/livro/{livroId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ComentarioDTO>> listarPorLivro(
            @PathVariable Integer livroId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ComentarioDTO> comentarios = comentarioService.listarPorLivro(livroId, page, size);
        String mensagem = comentarios.isEmpty() ? "Nenhum comentário encontrado para este livro" : "Comentários encontrados para este livro";
        return new ServiceResponse<>(comentarios, mensagem, !comentarios.isEmpty(), getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void excluirComentario(@PathVariable Integer id) {
        comentarioService.excluirComentario(id);
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
