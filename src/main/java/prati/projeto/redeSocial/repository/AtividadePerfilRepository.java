package prati.projeto.redeSocial.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import prati.projeto.redeSocial.modal.entity.AtividadePerfil;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;

import java.time.LocalDate;
import java.util.List;


@Repository
public interface AtividadePerfilRepository extends JpaRepository <AtividadePerfil, Long>{

    AtividadePerfil findByPerfilAndData(Perfil perfil, LocalDate data);
    List<AtividadePerfil> findByPerfilAndDataAfter(PerfilDTO perfil, LocalDate data);

}
