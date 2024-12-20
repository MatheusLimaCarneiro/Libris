package prati.projeto.redeSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Avaliacao;

public interface AvaliacaoRepository extends JpaRepository<Avaliacao, Integer> {
}
