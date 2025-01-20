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

    @GetMapping("{email}")
    public Usuario getUsuarioId(@PathVariable String email) {
        return usuarioService.getUsuarioByEmail(email);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario saveUsuario(@RequestBody @Valid Usuario usuario) {
        return usuarioService.saveUsuario(usuario);
    }

    @DeleteMapping("{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuario(@PathVariable String email) {
        usuarioService.deleteUsuario(email);
    }

    @PutMapping("{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void updateUsuario(@PathVariable String email,
                              @RequestBody @Valid Usuario usuario) {
        usuarioService.updateUsuario(email, usuario);
    }
}
