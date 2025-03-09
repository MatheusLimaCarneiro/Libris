package prati.projeto.redeSocial.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prati.projeto.redeSocial.modal.entity.AtividadePerfil;
import prati.projeto.redeSocial.modal.entity.Perfil;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface AtividadePerfilRepository extends JpaRepository<AtividadePerfil, Long> {
    AtividadePerfil findByPerfilAndData(Perfil perfil, LocalDate data);
    List<AtividadePerfil> findByPerfilAndDataAfter(Perfil perfil, LocalDate data);
    Page<AtividadePerfil> findByPerfilIdAndDataAfter(Integer perfilId, LocalDate data, Pageable pageable);}