package prati.projeto.redeSocial.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import prati.projeto.redeSocial.exception.RegraNegocioException;
import prati.projeto.redeSocial.modal.entity.Livro;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.modal.entity.StatusLeitura;
import prati.projeto.redeSocial.modal.enums.StatusLeituraEnum;
import prati.projeto.redeSocial.repository.LivroRepository;
import prati.projeto.redeSocial.repository.PerfilRepository;
import prati.projeto.redeSocial.repository.StatusLeituraRepository;
import prati.projeto.redeSocial.rest.dto.StatusLeituraDTO;
import prati.projeto.redeSocial.service.StatusLeituraService;

@Service
@RequiredArgsConstructor
public class StatusLeituraServiceImpl implements StatusLeituraService {

    private final StatusLeituraRepository statusLeituraRepository;
    private final PerfilRepository perfilRepository;
    private final LivroRepository livroRepository;

    @Override
    public StatusLeituraDTO salvarStatus(Integer perfilId, Integer livroId, StatusLeituraEnum statusLeituraEnum) {
        Perfil perfil = perfilRepository.findById(perfilId)
                .orElseThrow(() -> new RegraNegocioException("Perfil não encontrado"));
        Livro livro = livroRepository.findById(livroId)
                .orElseThrow(() -> new RegraNegocioException("Livro não encontrado"));
        StatusLeitura statusLeitura = new StatusLeitura();
        statusLeitura.setPerfil(perfil);
        statusLeitura.setLivro(livro);
        statusLeitura.setStatusLeitura(statusLeituraEnum);
        StatusLeitura salvo = statusLeituraRepository.save(statusLeitura);
        return convertToDTO(salvo);
    }

    @Override
    public StatusLeituraDTO mudarStatus(Integer id, StatusLeituraEnum novoStatus) {
        StatusLeitura statusLeitura = statusLeituraRepository.findById(id)
                .orElseThrow(() -> new RegraNegocioException("Status de leitura não encontrado"));
        statusLeitura.setStatusLeitura(novoStatus);

        StatusLeitura salvo = statusLeituraRepository.save(statusLeitura);
        return convertToDTO(salvo);
    }

    private StatusLeituraDTO convertToDTO(StatusLeitura statusLeitura) {
        return new StatusLeituraDTO(
                statusLeitura.getId(),
                statusLeitura.getPerfil().getId(),
                statusLeitura.getLivro().getId(),
                statusLeitura.getStatusLeitura()
        );
    }
}
