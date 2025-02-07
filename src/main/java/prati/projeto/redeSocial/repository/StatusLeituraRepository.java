package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.StatusLeitura;

import java.util.Optional;

public interface StatusLeituraRepository extends JpaRepository<StatusLeitura, Integer> {
    Optional<StatusLeitura> findByPerfilAndLivro(Perfil perfil, Livro livro);
    Page<StatusLeitura> findAll(Pageable pageable);
}
