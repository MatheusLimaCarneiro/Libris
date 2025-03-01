package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.LivroException;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.rest.dto.LivroResponseDTO;
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
    public Livro saveLivro(LivroResponseDTO dto) {

        validarGoogleIdUnico(dto.getGoogleId());

        validarIsbnDuplicado(dto.getIsbn());

        Livro livro = convertToEntity(dto);

        return livroRepository.save(livro);
    }

    @Override
    public void deleteLivro(Integer id) {
        Livro livro = getLivroById(id);
        livroRepository.delete(livro);
    }

    @Override
    @Transactional
    public void updateLivro(Integer id, LivroResponseDTO dto) {
        Livro livroExistente = getLivroById(id);

        if (!livroExistente.getIsbn().equals(dto.getIsbn()) &&
                livroRepository.existsByIsbn(dto.getIsbn())) {
            throw new LivroException("ISBN já cadastrado");
        }

        livroExistente.setGoogleId(dto.getGoogleId());
        livroExistente.setTitulo(dto.getTitulo());
        livroExistente.setSubtitulo(dto.getSubtitulo());
        livroExistente.setAutores(dto.getAutores());
        livroExistente.setEditora(dto.getEditora());
        livroExistente.setSinopse(dto.getSinopse());
        livroExistente.setNumeroPaginas(dto.getNumeroPaginas());
        livroExistente.setIsbn(dto.getIsbn());
        livroExistente.setIdioma(dto.getIdioma());
        livroExistente.setCategoria(dto.getCategoria());
        livroExistente.setUrl_capa(dto.getUrl_capa());
        livroExistente.setLinkCompra(dto.getLinkCompra());
        livroExistente.setDataPublicacao(dto.getDataPublicacao());

        livroRepository.save(livroExistente);
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

        return livroPage.map(this::convertToResumidoDTO);
    }

    @Override
    public Livro getLivroByGoogleId(String googleId) {
        return livroRepository.findByGoogleId(googleId)
                .orElseThrow(() -> new LivroException("Livro com Google ID " + googleId + " não encontrado"));
    }

    private void validarIsbnDuplicado(String isbn) {
        if (livroRepository.existsByIsbn(isbn)) {
            throw new LivroException("ISBN já cadastrado");
        }
    }

    public void validarGoogleIdUnico(String googleId) {
        if (livroRepository.existsByGoogleId(googleId)) {
            throw new LivroException("Google ID já cadastrado!");
        }
    }


    private Livro convertToEntity(LivroResponseDTO dto) {
        Livro livro = new Livro();
        livro.setGoogleId(dto.getGoogleId());
        livro.setTitulo(dto.getTitulo());
        livro.setSubtitulo(dto.getSubtitulo());
        livro.setAutores(dto.getAutores());
        livro.setEditora(dto.getEditora());
        livro.setSinopse(dto.getSinopse());
        livro.setNumeroPaginas(dto.getNumeroPaginas());
        livro.setIsbn(dto.getIsbn());
        livro.setIdioma(dto.getIdioma());
        livro.setCategoria(dto.getCategoria());
        livro.setUrl_capa(dto.getUrl_capa());
        livro.setLinkCompra(dto.getLinkCompra());
        livro.setDataPublicacao(dto.getDataPublicacao());
        return livro;
    }

    private LivroResumidoDTO convertToResumidoDTO(Livro livro) {
        return new LivroResumidoDTO(
                livro.getTitulo(),
                livro.getAutores(),
                livro.getDataPublicacao().toString()
        );
    }
}
