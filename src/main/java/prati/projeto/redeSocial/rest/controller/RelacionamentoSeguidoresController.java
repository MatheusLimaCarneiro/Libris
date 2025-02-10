package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.PerfilResumidoDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.RelacionamentoSeguidoresService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/relacionamentos")
public class RelacionamentoSeguidoresController {

    @Autowired
    private RelacionamentoSeguidoresService relacionamentoService;

    @PostMapping("/seguir/{seguidorId}/{seguidoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<Void> seguirPerfil(
            @PathVariable @Positive(message = "ID do seguidor deve ser positivo") Integer seguidorId,
            @PathVariable @Positive(message = "ID do seguido deve ser positivo") Integer seguidoId) {
        relacionamentoService.seguirPerfil(seguidorId, seguidoId);
        return new ServiceResponse<>(null, "Perfil seguido com sucesso", true, getFormattedTimestamp());
    }

    @DeleteMapping("/deixar-de-seguir/{seguidorId}/{seguidoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deixarDeSeguir(
            @PathVariable @Positive(message = "ID do seguidor deve ser positivo") Integer seguidorId,
            @PathVariable @Positive(message = "ID do seguido deve ser positivo") Integer seguidoId) {
        relacionamentoService.deixarDeSeguir(seguidorId, seguidoId);
    }

    @GetMapping("/esta-seguindo/{seguidorId}/{seguidoId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Boolean> estaSeguindo(
            @PathVariable Integer seguidorId,
            @PathVariable Integer seguidoId) {
        boolean resultado = relacionamentoService.estaSeguindo(seguidorId, seguidoId);
        return new ServiceResponse<>(resultado, "Verificação de seguimento", true, getFormattedTimestamp());
    }

    @GetMapping("/seguidores/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<PerfilResumidoDTO>> buscarSeguidores(
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PerfilResumidoDTO> seguidores = relacionamentoService.buscarSeguidores(perfilId, page, size);
        String mensagem = seguidores.isEmpty() ? "Nenhum seguidor encontrado" : "Seguidores encontrados";
        return new ServiceResponse<>(seguidores, mensagem, !seguidores.isEmpty(), getFormattedTimestamp());
    }

    @GetMapping("/seguindo/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<PerfilResumidoDTO>> buscarSeguindo(
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PerfilResumidoDTO> seguindo = relacionamentoService.buscarSeguindo(perfilId, page, size);
        String mensagem = seguindo.isEmpty() ? "Não está seguindo ninguém" : "Seguindo encontrados";
        return new ServiceResponse<>(seguindo, mensagem, !seguindo.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
