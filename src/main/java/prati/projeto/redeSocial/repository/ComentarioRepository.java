package prati.projeto.redeSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Comentario;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
}
