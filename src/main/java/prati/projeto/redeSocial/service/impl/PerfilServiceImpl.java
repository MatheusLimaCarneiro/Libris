package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;
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
        if (perfil.getUsuario() == null || perfil.getUsuario().getEmail() == null) {
            throw new RegraNegocioException("Usuário é obrigatório");
        }

        Usuario usuario = usuarioRepository.findById(perfil.getUsuario().getEmail())
                .orElseThrow(() -> new RegraNegocioException(
                        "Usuário com EMAIL " + perfil.getUsuario().getEmail() + " não encontrado"));

        if (perfilRepository.existsByUsuarioEmail(usuario.getEmail())) {
            throw new RegraNegocioException("Usuário já possui um perfil");
        }

        perfil.setUsuario(usuario);
        Perfil savedPerfil = perfilRepository.save(perfil);
        return convertToDTO(savedPerfil);
    }

    @Override
    public void deletePerfil(Integer id) {
        perfilRepository.findById(id)
                .ifPresentOrElse(
                        perfilRepository::delete,
                        () -> { throw new RegraNegocioException("Perfil não encontrado"); }
                );
    }

    @Override
    public void updatePerfil(Integer id, Perfil perfil) {
        perfilRepository.findById(id)
                .map(perfilExistente -> {
                    perfil.setId(perfilExistente.getId());
                    perfil.setUsuario(perfilExistente.getUsuario());
                    return perfilRepository.save(perfil);
                })
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));
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
