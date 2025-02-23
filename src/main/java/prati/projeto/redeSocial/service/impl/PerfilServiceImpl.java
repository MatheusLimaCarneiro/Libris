package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;
import prati.projeto.redeSocial.rest.dto.PerfilResumidoDTO;
import prati.projeto.redeSocial.rest.dto.UsuarioResumidoDTO;
import prati.projeto.redeSocial.service.PerfilService;

@Service
@RequiredArgsConstructor
public class PerfilServiceImpl implements PerfilService {

    private final PerfilRepository perfilRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public PerfilDTO getPerfilById(Integer id) {
        Perfil perfil = perfilRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException(
                        "Perfil com ID " + id + " não encontrado"));
        return convertToDTO(perfil);
    }

    @Override
    public PerfilDTO savePerfil(Perfil perfil) {
        Usuario usuarioCompleto = verificarUsuario(perfil.getUsuario());

        if (perfilRepository.existsByUsuarioEmail(usuarioCompleto.getEmail())) {
            throw new RegraNegocioException("Usuário já possui um perfil");
        }

        perfil.setUsuario(usuarioCompleto);

        Perfil savedPerfil = perfilRepository.save(perfil);
        return convertToDTO(savedPerfil);
    }

    @Override
    public void deletePerfil(Integer id) {
        perfilRepository.findById(id)
                .ifPresentOrElse(perfil -> {
                    perfil.setUsuario(null);
                    perfilRepository.delete(perfil);
                }, () -> {
                    throw new RegraNegocioException("Perfil não encontrado");
                });
    }

    @Override
    public void updatePerfil(Integer id, Perfil perfil) {
        perfilRepository.findById(id)
                .map(perfilExistente -> {
                    perfil.setId(perfilExistente.getId());

                    Usuario usuarioExistente = perfilExistente.getUsuario();
                    perfil.setUsuario(usuarioExistente);

                    return perfilRepository.save(perfil);
                })
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));
    }

    @Override
    public Page<PerfilResumidoDTO> listarPerfil(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Perfil> perfilPage = perfilRepository.findAll(pageable);

        return perfilPage.map(perfil -> new PerfilResumidoDTO(
           perfil.getUrlPerfil(),
                perfil.getResumoBio(),
                perfil.getUsuario().getUsername()
        ));
    }

    @Override
    public PerfilResumidoDTO buscarPorUsername(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado para o username: " + username));

        Perfil perfil = perfilRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado para o usuário: " + username));

        return new PerfilResumidoDTO(
                perfil.getUrlPerfil(),
                perfil.getResumoBio(),
                perfil.getUsuario().getUsername()
        );
    }

    private Usuario verificarUsuario(Usuario usuario) {
        if (usuario == null || usuario.getEmail() == null) {
            throw new RegraNegocioException("Usuário é obrigatório");
        }

        return usuarioRepository.findById(usuario.getEmail())
                .orElseThrow(() -> new RegraNegocioException(
                        "Usuário com EMAIL " + usuario.getEmail() + " não encontrado"));
    }

    private PerfilDTO convertToDTO(Perfil perfil) {
        PerfilDTO dto = new PerfilDTO();
        dto.setId(perfil.getId());
        dto.setUrlPerfil(perfil.getUrlPerfil());
        dto.setResumoBio(perfil.getResumoBio());
        dto.setSeguindo(perfil.getSeguindo());
        dto.setSeguidores(perfil.getSeguidores());
        dto.setGenerosFavoritos(perfil.getGenerosFavoritos());
        dto.setUrlBackPerfil(perfil.getUrlBackPerfil());

        if (perfil.getUsuario() != null) {
            dto.setUsuario(new UsuarioResumidoDTO(
                    perfil.getUsuario().getUsername(),
                    perfil.getUsuario().getEmail()
            ));
        }

        return dto;
    }
}
