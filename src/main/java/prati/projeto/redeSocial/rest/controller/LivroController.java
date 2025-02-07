package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.rest.dto.LivroResumidoDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.LivroService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;


@RestController
@RequestMapping("/libris/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Livro> getLivroById(@PathVariable Integer id) {
        Livro livro = livroService.getLivroById(id);
        return new ApiResponse<>(livro, "Livro encontrado", true, getFormattedTimestamp());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<Livro> saveLivro(@RequestBody @Valid Livro livro) {
        Livro savedLivro = livroService.saveLivro(livro);
        return new ApiResponse<>(savedLivro, "Livro salvo com sucesso", true, getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLivro(@PathVariable Integer id) {
        livroService.deleteLivro(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> updateLivro(@PathVariable Integer id, @RequestBody @Valid Livro livro) {
        livroService.updateLivro(id, livro);
        return new ApiResponse<>(null, "Livro atualizado com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/search")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<List<Livro>> findLivro(
            @RequestParam(required = false) String titulo,
            @RequestParam(required = false) String autores) {

        List<Livro> livros = livroService.findLivro(titulo, autores);
        String mensagem = livros.isEmpty() ? "Nenhum livro encontrado" : "Livros encontrados";
        return new ApiResponse<>(livros.isEmpty() ? null : livros, mensagem, !livros.isEmpty(), getFormattedTimestamp());
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<LivroResumidoDTO>> getAllLivros(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<LivroResumidoDTO> livros = livroService.getAllLivros(page, size);
        String mensagem = livros.isEmpty() ? "Nenhum livro encontrado" : "Livros encontrados";
        return new ApiResponse<>(livros, mensagem, !livros.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
