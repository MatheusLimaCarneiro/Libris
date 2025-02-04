package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.ResenhaDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaViewDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.ResenhaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/libris/resenhas")
public class ResenhaController {

    @Autowired
    private ResenhaService resenhaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Integer> save(@RequestBody @Valid ResenhaDTO dto) {
        Integer resenhaId = resenhaService.saveResenha(dto);
        return new ApiResponse<>(resenhaId, "Resenha salva com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<ResenhaViewDTO> getById(@PathVariable Integer id) {
        ResenhaViewDTO resenha = resenhaService.getResenhaById(id);
        return new ApiResponse<>(resenha, "Resenha encontrada", true, getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        resenhaService.deleteResenha(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> update(@PathVariable Integer id, @Valid @RequestBody ResenhaDTO dto) {
        resenhaService.updateResenha(id, dto);
        return new ApiResponse<>(null, "Resenha atualizada com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/livro/{livroId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ResenhaViewDTO>> findByLivro(@PathVariable Integer livroId) {
        List<ResenhaViewDTO> resenhas = resenhaService.findByLivro(livroId);
        return new ApiResponse<>(resenhas, "Resenhas do livro encontradas", true, getFormattedTimestamp());
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<ResenhaViewDTO>> findAll() {
        List<ResenhaViewDTO> resenhas = resenhaService.findAllResenhas();
        return new ApiResponse<>(resenhas, "Lista de todas as resenhas", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}