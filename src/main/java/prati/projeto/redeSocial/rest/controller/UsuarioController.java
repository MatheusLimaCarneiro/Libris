package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.service.UsuarioService;

@RestController
@RequestMapping("/libris/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("{id}")
    public Usuario getUsuarioId(@PathVariable Integer id) {
        return usuarioService.getUsuarioById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario saveUsuario(@RequestBody @Valid Usuario usuario) {
        return usuarioService.saveUsuario(usuario);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuario(@PathVariable Integer id) {
        usuarioService.deleteUsuario(id);
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUsuario(@PathVariable Integer id,
                              @RequestBody @Valid Usuario usuario) {
        usuarioService.updateUsuario(id, usuario);
    }
}
