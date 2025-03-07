package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import prati.projeto.redeSocial.modal.entity.Notificacao;


public interface NotificacaoRepository extends JpaRepository<Notificacao, Integer> {

    Page<Notificacao> findByDestinatarioId(Integer destinatarioId, Pageable pageable);

    @Query("SELECT n FROM Notificacao n JOIN n.destinatario d JOIN d.usuario u WHERE u.username = :username")
    Page<Notificacao> findByDestinatarioUsername(@Param("username") String username, Pageable pageable);

    @Modifying
    @Query("UPDATE Notificacao n SET n.lida = true WHERE n.destinatario.id = :destinatarioId AND n.lida = false")
    void marcarTodasComoLida(@Param("destinatarioId") Integer destinatarioId);

    @Modifying
    @Query("DELETE FROM Notificacao n WHERE n.destinatario.id = :destinatarioId")
    void deletarTodasPorDestinatarioId(@Param("destinatarioId") Integer destinatarioId);

    void deleteByDestinatarioUsuarioUsername(String username);
}
