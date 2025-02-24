package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.ResenhaDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaViewDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.ResenhaService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/resenhas")
public class ResenhaController {

    @Autowired
    private ResenhaService resenhaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<ResenhaViewDTO> save(@RequestBody @Valid ResenhaDTO dto) {
        ResenhaViewDTO resenhaId = resenhaService.saveResenha(dto);
        return new ServiceResponse<>(resenhaId, "Resenha salva com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<ResenhaViewDTO> getById(@PathVariable Integer id) {
        ResenhaViewDTO resenha = resenhaService.getResenhaById(id);
        return new ServiceResponse<>(resenha, "Resenha encontrada", true, getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        resenhaService.deleteResenha(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Void> update(@PathVariable Integer id, @Valid @RequestBody ResenhaDTO dto) {
        resenhaService.updateResenha(id, dto);
        return new ServiceResponse<>(null, "Resenha atualizada com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/livro/{livroId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ResenhaViewDTO>> findByLivro(
            @PathVariable Integer livroId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ResenhaViewDTO> resenhasPage = resenhaService.findByLivro(livroId, page, size);
        return new ServiceResponse<>(resenhasPage, "Resenhas do livro encontradas", true, getFormattedTimestamp());
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<ResenhaViewDTO>> getAllResenha(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<ResenhaViewDTO> resenhas = resenhaService.findAllResenhas(page, size);
        String mensagem = resenhas.isEmpty() ? "Nenhuma resenha encontrada" : "Resenhas encontradas";
        return new ServiceResponse<>(resenhas, mensagem, !resenhas.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}