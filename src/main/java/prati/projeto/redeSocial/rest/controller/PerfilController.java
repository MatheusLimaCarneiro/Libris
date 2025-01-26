package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;
import prati.projeto.redeSocial.service.PerfilService;

@RestController
@RequestMapping("/libris/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("{id}")
    public PerfilDTO getPerfilById(@PathVariable Integer id) {
        return perfilService.getPerfilById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PerfilDTO savePerfil(@RequestBody @Valid Perfil perfil) {
        return perfilService.savePerfil(perfil);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerfil(@PathVariable Integer id) {
        perfilService.deletePerfil(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updatePerfil(@PathVariable Integer id,
                             @RequestBody @Valid Perfil perfil) {
        perfilService.updatePerfil(id, perfil);
    }
}
