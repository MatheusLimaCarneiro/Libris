package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prati.projeto.redeSocial.modal.entity.RelacionamentoSeguidores;

import java.util.List;
import java.util.Optional;

public interface RelacionamentoSeguidoresRepository extends JpaRepository<RelacionamentoSeguidores, Integer> {
    @Query("SELECT COUNT(r) FROM RelacionamentoSeguidores r WHERE r.seguido.id = :perfilId")
    Integer contarSeguidores(@Param("perfilId") Integer perfilId);

    @Query("SELECT COUNT(r) FROM RelacionamentoSeguidores r WHERE r.seguidor.id = :perfilId")
    Integer contarSeguindo(@Param("perfilId") Integer perfilId);

    Optional<RelacionamentoSeguidores> findBySeguidorIdAndSeguidoId(Integer seguidorId, Integer seguidoId);

    @Query("SELECT r FROM RelacionamentoSeguidores r JOIN FETCH r.seguidor JOIN FETCH r.seguidor.usuario WHERE r.seguido.id = :perfilId")
    Page<RelacionamentoSeguidores> findBySeguidoId(@Param("perfilId") Integer perfilId, Pageable pageable);

    @Query("SELECT r FROM RelacionamentoSeguidores r JOIN FETCH r.seguido JOIN FETCH r.seguido.usuario WHERE r.seguidor.id = :perfilId")
    Page<RelacionamentoSeguidores> findBySeguidorId(@Param("perfilId") Integer perfilId, Pageable pageable);
}
