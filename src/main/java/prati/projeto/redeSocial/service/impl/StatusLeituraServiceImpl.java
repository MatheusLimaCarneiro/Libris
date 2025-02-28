package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.StatusLeitura;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.modal.enums.StatusLeituraEnum;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.StatusLeituraRepository;
import prati.projeto.redeSocial.repository.UsuarioRepository;
import prati.projeto.redeSocial.rest.dto.StatusLeituraDTO;
import prati.projeto.redeSocial.service.StatusLeituraService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class StatusLeituraServiceImpl implements StatusLeituraService {

    private final StatusLeituraRepository statusLeituraRepository;
    private final PerfilRepository perfilRepository;
    private final LivroRepository livroRepository;
    private final UsuarioRepository usuarioRepository;

    @Override
    public StatusLeituraDTO salvarStatus(Integer perfilId, String livroId, StatusLeituraEnum statusLeituraEnum, Integer pagina) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));
        Livro livro = livroRepository.findByGoogleId(livroId)
                .orElseThrow(() -> new RegraNegocioException("Livro não encontrado"));

        if (pagina < 1 || pagina > livro.getNumeroPaginas()) {
            throw new RegraNegocioException("A página deve estar entre 1 e " + livro.getNumeroPaginas());
        }

        Optional<StatusLeitura> statusExistente = statusLeituraRepository.findByPerfilAndLivro(perfil, livro);

        if (statusExistente.isPresent()) {
            throw new RegraNegocioException("O perfil já possui um status para este livro.");
        }

        StatusLeitura novoStatus = new StatusLeitura();
        novoStatus.setPerfil(perfil);
        novoStatus.setLivro(livro);
        novoStatus.setGoogleIdLivro(livro.getGoogleId());
        novoStatus.setPagina(pagina);
        novoStatus.setStatusLeitura(statusLeituraEnum);

        StatusLeitura salvo = statusLeituraRepository.save(novoStatus);
        return convertToDTO(salvo);
    }

    @Override
    public StatusLeituraDTO mudarStatus(Integer id,Integer pagina, StatusLeituraEnum novoStatus) {
        StatusLeitura statusLeitura = statusLeituraRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Status de leitura não encontrado"));

        Livro livro = statusLeitura.getLivro();
        if (pagina < 1 || pagina > livro.getNumeroPaginas()) {
            throw new RegraNegocioException("A página deve estar entre 1 e " + livro.getNumeroPaginas());
        }
        statusLeitura.setStatusLeitura(novoStatus);
        statusLeitura.setPagina(pagina);

        StatusLeitura salvo = statusLeituraRepository.save(statusLeitura);
        return convertToDTO(salvo);
    }

    @Override
    public Page<StatusLeituraDTO> listarStatus(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        Page<StatusLeitura> statusPage = statusLeituraRepository.findAll(pageable);
        return statusPage.map(this::convertToDTO);
    }

    @Override
    public Page<StatusLeituraDTO> listarStatusPorPerfil(String username, int page, int size) {
        Usuario usuario = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RegraNegocioException("Usuário não encontrado com o username: " + username));

        Perfil perfil = perfilRepository.findByUsuario(usuario)
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado para o usuário: " + username));

        Pageable pageable = PageRequest.of(page, size);
        Page<StatusLeitura> statusPage = statusLeituraRepository.findByPerfil(perfil, pageable);

        return statusPage.map(this::convertToDTO);
    }

    private StatusLeituraDTO convertToDTO(StatusLeitura statusLeitura) {
        return new StatusLeituraDTO(
                statusLeitura.getId(),
                statusLeitura.getPerfil().getId(),
                statusLeitura.getLivro().getGoogleId(),
                statusLeitura.getPagina(),
                statusLeitura.getStatusLeitura()
        );
    }
}
