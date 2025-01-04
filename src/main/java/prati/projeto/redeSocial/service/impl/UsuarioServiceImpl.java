package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.service.UsuarioService;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario getUsuarioById(Integer id) {
        return usuarioRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                        "Usuario com ID " + id + " não encontrado"));
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email já cadastrado");
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(Integer id) {
        usuarioRepository.findById(id)
                .ifPresentOrElse(
                        usuarioRepository::delete,
                        () -> { throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"); }
                );
    }

    @Override
    public void updateUsuario(Integer id, Usuario usuario) {
        usuarioRepository.findById(id)
                .map(usuarioExistente -> {
                    usuario.setId(usuarioExistente.getId());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Usuario não encontrado"));
    }
}
