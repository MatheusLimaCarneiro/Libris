package prati.projeto.redeSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Avaliacao;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    List<Avaliacao> findByPerfilId(Integer perfilId);
    List<Avaliacao> findByResenhaId(Integer resenhaId);
    boolean existsByPerfilIdAndResenhaId(Integer perfilId, Integer resenhaId);
}
