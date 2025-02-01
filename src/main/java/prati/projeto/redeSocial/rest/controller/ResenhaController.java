package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.ResenhaDTO;
import prati.projeto.redeSocial.rest.dto.ResenhaViewDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.ResenhaService;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/libris/resenhas")
public class ResenhaController {

    @Autowired
    private ResenhaService resenhaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Integer>> save(@RequestBody @Valid ResenhaDTO dto) {
        Integer resenhaId = resenhaService.saveResenha(dto);
        ApiResponse<Integer> response = new ApiResponse<>(resenhaId, "Resenha salva com sucesso", true, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResenhaViewDTO getById(@PathVariable Integer id) {
        return resenhaService.getResenhaById(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id) {
        resenhaService.deleteResenha(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @Valid @RequestBody ResenhaDTO dto) {
        resenhaService.updateResenha(id, dto);
    }

    @GetMapping("/livro/{livroId}")
    public List<ResenhaViewDTO> findByLivro(@PathVariable Integer livroId) {
        return resenhaService.findByLivro(livroId);
    }

    @GetMapping
    public List<ResenhaViewDTO> findAll() {
        return resenhaService.findAllResenhas();
    }

}