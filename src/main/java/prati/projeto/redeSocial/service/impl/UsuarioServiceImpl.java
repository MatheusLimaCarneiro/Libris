package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.config.PasswordConfig;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.rest.dto.UsuarioResumidoDTO;
import prati.projeto.redeSocial.service.UsuarioService;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final PasswordConfig passwordConfig;
    private final UsuarioRepository usuarioRepository;

    @Override
    public UsuarioResumidoDTO getUsuarioByEmail(String email) {
        Usuario usuario = usuarioRepository.findById(email)
                .orElseThrow(() -> new RegraNegocioException(
                        "Usuário com email " + email + " não encontrado"));

        return new UsuarioResumidoDTO(usuario.getUsername(), usuario.getEmail());
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
        Usuario usuarioExistente = usuarioRepository.findById(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        if (!usuarioExistente.getUsername().equals(usuario.getUsername())) {
            throw new RegraNegocioException("Alteração de username não permitida.");
        }

        String senhaCriptografada = passwordConfig.passwordEncoder().encode(usuario.getSenha());
        usuarioExistente.setSenha(senhaCriptografada);

        usuarioRepository.save(usuarioExistente);
    }
}
