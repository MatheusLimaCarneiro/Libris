package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Resenha;

import java.util.List;

public interface ResenhaRepository extends JpaRepository<Resenha, Integer> {
    Page<Resenha> findByLivroId(Integer livroId, Pageable pageable);
    Page<Resenha> findAll(Pageable pageable);
    Resenha findByPerfilIdAndLivroId(Integer perfilId, Integer livroId);
}
