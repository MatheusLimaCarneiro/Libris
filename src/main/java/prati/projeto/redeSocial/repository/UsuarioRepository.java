package prati.projeto.redeSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String> {

    boolean existsByEmail(String email);
    boolean existsByUsername(String username);
}
