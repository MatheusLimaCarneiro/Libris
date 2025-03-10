package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.config.PasswordConfig;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.exception.TokenInvalidException;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.rest.dto.UsuarioResumidoDTO;
import prati.projeto.redeSocial.security.JwtService;
import prati.projeto.redeSocial.service.email.EmailService;
import prati.projeto.redeSocial.service.UsuarioService;

import java.time.Duration;

@Service
@RequiredArgsConstructor
public class UsuarioServiceImpl implements UsuarioService{

    private final PasswordConfig passwordConfig;
    private final UsuarioRepository usuarioRepository;
    private final EmailService emailService;
    private final JwtService jwtService;


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

    @Override
    public void requestPasswordReset(String email) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        String token = jwtService.criarTokenEmail(email, Duration.ofHours(1));

        usuario.setResetToken(token);
        usuarioRepository.save(usuario);

        String resetLink = "http://localhost:5173/reset-password?token=" + token;
        String emailText = "Clique no link para redefinir sua senha: " + resetLink;
        emailService.sendEmail(email, "Redefinição de Senha", emailText);
    }

    @Override
    public void resetPassword(String token, String newPassword) {
        if (!jwtService.tokenValido(token)) {
            throw new TokenInvalidException("Token inválido ou expirado");
        }

        String email = jwtService.obterLoginUsuario(token);

        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        String senhaCriptografada = passwordConfig.passwordEncoder().encode(newPassword);
        usuario.setSenha(senhaCriptografada);
        usuario.setResetToken(null);
        usuarioRepository.save(usuario);
    }

    @Override
    public void changePassword(String email, String oldPassword, String newPassword) {
        Usuario usuario = usuarioRepository.findByEmail(email)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        // Verificar se a senha atual está correta
        if (!passwordConfig.passwordEncoder().matches(oldPassword, usuario.getSenha())) {
            throw new RegraNegocioException("Senha atual incorreta");
        }

        // Criptografar a nova senha
        String senhaCriptografada = passwordConfig.passwordEncoder().encode(newPassword);
        usuario.setSenha(senhaCriptografada);

        // Salvar a nova senha
        usuarioRepository.save(usuario);
    }
}
