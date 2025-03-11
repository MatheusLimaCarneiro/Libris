package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Livro;

import java.util.List;
import java.util.Optional;

public interface LivroRepository extends JpaRepository<Livro, Integer> {
    boolean existsByIsbn(String isbn);
    List<Livro> findByTituloContainingIgnoreCase(String titulo);
    List<Livro> findByAutoresContainingIgnoreCase(String autores);
    List<Livro> findByTituloContainingIgnoreCaseAndAutoresContainingIgnoreCase(String titulo, String autores);
    Page<Livro> findAll(Pageable pageable);
    boolean existsByGoogleId(String googleId);
    Optional<Livro> findByGoogleId(String googleId);
}
