package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.dto.ResenhaDTO;
import prati.projeto.redeSocial.dto.ResenhaViewDTO;

import java.util.List;

public interface ResenhaService {
    ResenhaViewDTO getResenhaById(Integer id);

    Integer saveResenha(ResenhaDTO resenhaDTO);

    void deleteResenha(Integer id);

    void updateResenha(Integer id, ResenhaDTO resenhaDTO);

    List<ResenhaViewDTO> findByLivro(Integer livroId);
}
