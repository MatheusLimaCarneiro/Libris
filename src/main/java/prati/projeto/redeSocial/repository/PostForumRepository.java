package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prati.projeto.redeSocial.modal.entity.PostForum;

public interface PostForumRepository extends JpaRepository<PostForum, Integer> {
    Page<PostForum> findAll(Pageable pageable);

    @Query("SELECT p FROM PostForum p " +
            "WHERE (:tags IS NULL OR p.tags LIKE %:tags%) " +
            "AND (:username IS NULL OR p.perfil.usuario.username LIKE %:username%) " +
            "AND (:livroNome IS NULL OR p.livro.titulo LIKE %:livroNome%)")
    Page<PostForum> filtrarPosts(
            @Param("tags") String tags,
            @Param("username") String username,
            @Param("livroNome") String livroNome,
            Pageable pageable
    );
}
