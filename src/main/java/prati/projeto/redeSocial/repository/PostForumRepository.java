package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.PostForum;

public interface PostForumRepository extends JpaRepository<PostForum, Integer> {
    Page<PostForum> findAll(Pageable pageable);
}
