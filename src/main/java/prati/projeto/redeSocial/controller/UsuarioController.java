package prati.projeto.redeSocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.UsuarioRepository;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @GetMapping("{id}")
    public Usuario getUsuarioId(@PathVariable Integer id){

        return usuarioRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                "Usuario com ID " + id + " não encontrado"));
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Usuario save(@RequestBody Usuario usuario){

        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    @DeleteMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Integer id){
        usuarioRepository.findById(id)
                .ifPresentOrElse(
                        usuario -> usuarioRepository.delete(usuario),
                                () ->{throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado");}
                                );
    }

    @PutMapping("{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void update(@PathVariable Integer id,
                       @RequestBody Usuario usuario){
        usuarioRepository.findById(id)
                .map(usuarioExistente ->{
                    usuario.setId((usuarioExistente.getId()));
                    usuarioRepository.save(usuario);
                    return usuario;
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario não encontrado"));
    }
}
