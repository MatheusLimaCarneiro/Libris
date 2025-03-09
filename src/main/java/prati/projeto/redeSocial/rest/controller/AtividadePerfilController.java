package prati.projeto.redeSocial.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.AtividadePerfilDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.AtividadePerfilService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@RestController
@RequestMapping("/libris/atividade")
public class AtividadePerfilController {

    @Autowired
    private AtividadePerfilService atividadePerfilService;

    @GetMapping("/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<List<AtividadePerfilDTO>> listarAtividades(
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "60") int dias) {
        List<AtividadePerfilDTO> atividades = atividadePerfilService.consultarUltimasAtividades(perfilId, dias);
        String mensagem = atividades.isEmpty() ? "Nenhuma atividade encontrada" : "Atividades encontradas";
        return new ServiceResponse<>(atividades, mensagem, !atividades.isEmpty(), getFormattedTimestamp());
    }

    @GetMapping("/paginado/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<AtividadePerfilDTO>> listarAtividadesPaginadas(
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "60") int dias) {
        Page<AtividadePerfilDTO> atividadesPage = atividadePerfilService.listarAtividadesPaginadas(perfilId, page, size, dias);
        String mensagem = atividadesPage.isEmpty() ? "Nenhuma atividade encontrada nos últimos " + dias + " dias" : "Atividades encontradas";
        return new ServiceResponse<>(atividadesPage, mensagem, !atividadesPage.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}