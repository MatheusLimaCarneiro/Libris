package prati.projeto.redeSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.ComentarioResposta;

import java.util.List;

public interface ComentarioRespostaRepository extends JpaRepository<ComentarioResposta, Integer> {
    List<ComentarioResposta> findByComentarioOriginalId(Integer comentarioId);
}
