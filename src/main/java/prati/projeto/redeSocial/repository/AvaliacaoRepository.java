package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Avaliacao;

import java.util.List;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {

    Page<Avaliacao> findByPerfilId(Integer perfilId, Pageable pageable);
    Page<Avaliacao> findByResenhaId(Integer resenhaId, Pageable pageable);
    boolean existsByPerfilIdAndResenhaId(Integer perfilId, Integer resenhaId);
    Page<Avaliacao> findAll(Pageable pageable);
}
