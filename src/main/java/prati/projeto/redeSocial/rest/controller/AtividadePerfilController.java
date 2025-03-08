package prati.projeto.redeSocial.rest.controller;

import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.AtividadePerfil;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.AtividadePerfilDTO;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;
import prati.projeto.redeSocial.service.AtividadePerfilService;
import prati.projeto.redeSocial.service.PerfilService;

import java.util.List;

@PermitAll
@RestController
@RequestMapping("/atividade")
public class AtividadePerfilController {


    private final AtividadePerfilService atividadePerfilService;
    private final PerfilService perfilService;

    public AtividadePerfilController(AtividadePerfilService atividadePerfilService, PerfilService perfilService) {
        this.atividadePerfilService = atividadePerfilService;
        this.perfilService = perfilService;
    }

    @GetMapping("/{perfilId}")
    public List<AtividadePerfilDTO> getAtividades(
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "30") int dias
    ) {
        // Supondo a existência de um serviço para buscar o perfil
        PerfilDTO perfil = perfilService.getPerfilById(perfilId);
        return atividadePerfilService.consultarUltimasAtividades(perfil, dias);
    }


}
