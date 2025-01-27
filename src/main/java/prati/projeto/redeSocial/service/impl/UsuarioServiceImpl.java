package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.service.UsuarioService;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final UsuarioRepository usuarioRepository;

    @Override
    public Usuario getUsuarioByEmail(String email) {
        return usuarioRepository.findById(email)
                .orElseThrow(() -> new RegraNegocioException(
                        "Usuario com email " + email + " não encontrado"));
    }

    @Override
    public Usuario saveUsuario(Usuario usuario) {
        if (usuarioRepository.existsByEmail(usuario.getEmail())) {
            throw new RegraNegocioException("Email já cadastrado");
        }
        if (usuarioRepository.existsByUsername(usuario.getUsername())) {
            throw new RegraNegocioException("Username já cadastrado");
        }
        return usuarioRepository.save(usuario);
    }

    @Override
    public void deleteUsuario(String email) {
        usuarioRepository.findById(email)
                .ifPresentOrElse(
                        usuarioRepository::delete,
                        () -> { throw new RegraNegocioException("Usuario não encontrado"); }
                );
    }

    @Override
    public void updateUsuario(String email, Usuario usuario) {
        usuarioRepository.findById(email)
                .map(usuarioExistente -> {
                    usuario.setEmail(usuarioExistente.getEmail());
                    return usuarioRepository.save(usuario);
                })
                .orElseThrow(() -> new RegraNegocioException("Usuario não encontrado"));
    }
}
