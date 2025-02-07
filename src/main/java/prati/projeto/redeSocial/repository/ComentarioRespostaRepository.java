package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.ComentarioResposta;

import java.util.List;

public interface ComentarioRespostaRepository extends JpaRepository<ComentarioResposta, Integer> {
    Page<ComentarioResposta> findByComentarioOriginalId(Integer comentarioId, Pageable pageable);
}
