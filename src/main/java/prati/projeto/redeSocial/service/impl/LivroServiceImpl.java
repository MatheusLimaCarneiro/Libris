package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.LivroException;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.rest.dto.LivroResumidoDTO;
import prati.projeto.redeSocial.service.LivroService;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LivroServiceImpl implements LivroService {

    private final LivroRepository livroRepository;

    @Override
    public Livro getLivroById(Integer id) {
        return livroRepository.findById(id)
                .orElseThrow(() -> new LivroException("Livro com ID " + id + " não encontrado"));
    }

    @Override
    public Livro saveLivro(Livro livro) {
        validarIsbnDuplicado(livro.getIsbn());
        return livroRepository.save(livro);
    }

    @Override
    public void deleteLivro(Integer id) {
        Livro livro = getLivroById(id);
        livroRepository.delete(livro);
    }

    @Override
    public void updateLivro(Integer id, Livro livro) {
        Livro livroExistente = getLivroById(id);

        if (!livroExistente.getIsbn().equals(livro.getIsbn()) &&
                livroRepository.existsByIsbn(livro.getIsbn())) {
            throw new LivroException("ISBN já cadastrado");
        }

        livro.setId(livroExistente.getId());
        livroRepository.save(livro);
    }

    @Override
    public List<Livro> findLivro(String titulo, String autores) {
        if (titulo != null && autores != null) {
            return livroRepository.findByTituloContainingIgnoreCaseAndAutoresContainingIgnoreCase(titulo, autores);
        } else if (titulo != null) {
            return livroRepository.findByTituloContainingIgnoreCase(titulo);
        } else if (autores != null) {
            return livroRepository.findByAutoresContainingIgnoreCase(autores);
        }
        return livroRepository.findAll();
    }

    @Override
    public Page<LivroResumidoDTO> getAllLivros(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Livro> livroPage = livroRepository.findAll(pageable);

        return livroPage.map(livro -> new LivroResumidoDTO(
                livro.getTitulo(),
                livro.getAutores(),
                livro.getDataPublicacao().toString()
        ));
    }

    private void validarIsbnDuplicado(String isbn) {
        if (livroRepository.existsByIsbn(isbn)) {
            throw new LivroException("ISBN já cadastrado");
        }
    }
}
