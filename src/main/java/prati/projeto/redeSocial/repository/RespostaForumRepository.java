package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.RespostaForum;

public interface RespostaForumRepository extends JpaRepository<RespostaForum, Integer> {
    Page<RespostaForum> findByComentarioForumId(Integer comentarioForumId, Pageable pageable);
}
