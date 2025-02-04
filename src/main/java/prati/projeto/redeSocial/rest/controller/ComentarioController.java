package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.ComentarioDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.ComentarioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/libris/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<ComentarioDTO> saveComentario(@RequestBody @Valid ComentarioDTO dto) {
        ComentarioDTO comentarioDTO = comentarioService.salvar(dto);
        return new ApiResponse<>(comentarioDTO, "Comentário criado com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ComentarioDTO> buscarPorId(@PathVariable Integer id) {
        ComentarioDTO comentarioDTO = comentarioService.buscarPorId(id);
        return new ApiResponse<>(comentarioDTO, "Comentário encontrado", true, getFormattedTimestamp());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ComentarioDTO>> listarTodos() {
        List<ComentarioDTO> comentarios = comentarioService.listarTodos();
        return new ApiResponse<>(comentarios, "Comentários listados com sucesso", true, getFormattedTimestamp());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ComentarioDTO> atualizarComentario(@PathVariable Integer id,
                                                          @RequestBody @Valid ComentarioDTO dto) {
        ComentarioDTO comentarioDTO = comentarioService.atualizarComentario(id, dto);
        return new ApiResponse<>(comentarioDTO, "Comentário atualizado com sucesso", true, getFormattedTimestamp());
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
