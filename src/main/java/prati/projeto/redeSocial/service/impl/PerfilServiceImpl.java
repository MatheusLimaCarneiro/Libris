package prati.projeto.redeSocial.service.impl;

import jakarta.transaction.Transactional;
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
import prati.projeto.redeSocial.rest.dto.PerfilRequestDTO;
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
    @Transactional
    public PerfilDTO savePerfil(PerfilRequestDTO perfilRequestDTO) {
        Usuario usuario = usuarioRepository.findById(perfilRequestDTO.getUsuarioEmail())
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado"));

        if (perfilRepository.existsByUsuarioEmail(usuario.getEmail())) {
            throw new RegraNegocioException("Usuário já possui um perfil");
        }

        Perfil perfil = new Perfil();
        perfil.setUrlPerfil(perfilRequestDTO.getUrlPerfil());
        perfil.setResumoBio(perfilRequestDTO.getResumoBio());
        perfil.setGenerosFavoritos(perfilRequestDTO.getGenerosFavoritos());
        perfil.setUrlBackPerfil(perfilRequestDTO.getUrlBackPerfil());
        perfil.setUsuario(usuario);

        Perfil savedPerfil = perfilRepository.save(perfil);

        return convertToDTO(savedPerfil);
    }

    @Override
    @Transactional
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
    @Transactional
    public void updatePerfil(Integer id, PerfilRequestDTO perfilRequestDTO) {
        perfilRepository.findById(id)
                .map(perfilExistente -> {
                        perfilExistente.setUrlPerfil(perfilRequestDTO.getUrlPerfil());
                        perfilExistente.setResumoBio(perfilRequestDTO.getResumoBio());
                        perfilExistente.setGenerosFavoritos(perfilRequestDTO.getGenerosFavoritos());
                        perfilExistente.setUrlBackPerfil(perfilRequestDTO.getUrlBackPerfil());

                        return perfilRepository.save(perfilExistente);
                })
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));
    }

    @Override
    public Page<PerfilResumidoDTO> listarPerfil(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<Perfil> perfilPage = perfilRepository.findAll(pageable);

        return perfilPage.map(perfil -> new PerfilResumidoDTO(
                perfil.getId(),
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
                perfil.getId(),
                perfil.getUrlPerfil(),
                perfil.getResumoBio(),
                perfil.getUsuario().getUsername()
        );
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
