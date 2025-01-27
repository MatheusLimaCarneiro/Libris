package prati.projeto.redeSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Perfil;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    boolean existsByUsuarioEmail(String usuarioEmail);
}
