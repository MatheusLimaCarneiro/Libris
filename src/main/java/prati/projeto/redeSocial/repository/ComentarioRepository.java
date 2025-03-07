package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Comentario;
import prati.projeto.redeSocial.modal.entity.Perfil;

public interface ComentarioRepository extends JpaRepository<Comentario, Integer> {
    Page<Comentario> findAll(Pageable pageable);
    Page<Comentario> findByGoogleIdLivro(String googleIdLivro, Pageable pageable);
    Page<Comentario> findByPerfil(Perfil perfil, Pageable pageable);
}
