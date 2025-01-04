package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.service.LivroService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;


    @Override
    public Livro getLivroById(Integer id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Livro com ID " + id + " não encontrado"));
    }

    @Override
    public Livro saveLivro(Livro livro) {
        validarIsbnDuplicado(livro.getIsbn());
        return livroRepository.save(livro);
    }

    @Override
    public void deleteLivro(Integer id) {
        livroRepository.findById(id)
                .ifPresentOrElse(
                        livroRepository::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Livro não encontrado"); }
                );
    }

    @Override
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

    @Override
    public List<Livro> findLivro(Livro filtro){
        ExampleMatcher matcher = ExampleMatcher
                .matching()
                .withIgnoreCase()
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example example = Example.of(filtro, matcher);

        return livroRepository.findAll(example);
    }

    private void validarIsbnDuplicado(String isbn) {
        if (livroRepository.existsByIsbn(isbn)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ISBN já cadastrado");
        }
    }
}
