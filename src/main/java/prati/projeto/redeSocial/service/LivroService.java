package prati.projeto.redeSocial.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.repository.LivroRepository;

import java.util.List;

@Service
public class LivroService {

    @Autowired
    private LivroRepository livroRepository;

    public Livro getLivroById(Integer id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Livro com ID " + id + " não encontrado"));
    }

    public Livro saveLivro(Livro livro) {
        if (livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN já cadastrado");
        }
        return livroRepository.save(livro);
    }

    public void deleteLivro(Integer id) {
        livroRepository.findById(id)
                .ifPresentOrElse(
                        livroRepository::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"); }
                );
    }

    public void updateLivro(Integer id, Livro livro) {
        livroRepository.findById(id)
                .map(livroExistente -> {
                    if (!livroExistente.getIsbn().equals(livro.getIsbn()) &&
                            livroRepository.existsByIsbn(livro.getIsbn())) {
                        throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN já cadastrado");
                    }
                    livro.setId(livroExistente.getId());
                    return livroRepository.save(livro);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"));
    }

    public List<Livro> findLivro(Livro filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);

        return livroRepository.findAll(example);
    }
}
