package prati.projeto.redeSocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.service.LivroService;

import java.util.List;


@RestController
@RequestMapping("/libris/livro")
public class LivroController {

    @Autowired
    private LivroService livroService;

    @GetMapping("{id}")
    public Livro getLivroId(@PathVariable Integer id) {
        return livroService.getLivroById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Livro saveLivro(@RequestBody Livro livro) {
        return livroService.saveLivro(livro);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteLivro(@PathVariable Integer id) {
        livroService.deleteLivro(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateLivro(@PathVariable Integer id, @RequestBody Livro livro) {
        livroService.updateLivro(id, livro);
    }

    @GetMapping("/search")
    public List<Livro> findLivro(@RequestParam(required = false) String titulo) {
        Livro filtro = new Livro();
        filtro.setTitulo(titulo);
        return livroService.findLivro(filtro);
    }
}
