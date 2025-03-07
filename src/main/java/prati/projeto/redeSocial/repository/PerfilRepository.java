package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.Usuario;

import java.util.Optional;

public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
    boolean existsByUsuarioEmail(String usuarioEmail);
    Optional<Perfil> findByUsuario(Usuario usuario);
    Page<Perfil> findAll(Pageable pageable);
}
