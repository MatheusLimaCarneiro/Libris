package prati.projeto.redeSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Resenha;

import java.util.List;

public interface ResenhaRepository extends JpaRepository<Resenha, Integer> {
    List<Resenha> findByLivroId(Integer livroId);
}
