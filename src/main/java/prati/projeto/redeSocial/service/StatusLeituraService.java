package prati.projeto.redeSocial.service;

import prati.projeto.redeSocial.modal.enums.StatusLeituraEnum;
import prati.projeto.redeSocial.rest.dto.StatusLeituraDTO;

public interface StatusLeituraService {

    StatusLeituraDTO salvarStatus(Integer perfilId, Integer livroId, StatusLeituraEnum statusLeituraEnum);

    StatusLeituraDTO mudarStatus(Integer id, StatusLeituraEnum novoStatus);

}
