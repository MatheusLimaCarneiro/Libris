package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.AvaliacaoDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.AvaliacaoService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/resenhas/{resenhaId}/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<AvaliacaoDTO> adicionarAvaliacao(
            @PathVariable Integer resenhaId,
            @RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
        AvaliacaoDTO resultado = avaliacaoService.adicionarAvaliacao(resenhaId, avaliacaoDTO);
        return new ApiResponse<>(resultado, "Avaliação adicionada com sucesso.", true, getFormattedTimestamp());
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<AvaliacaoDTO>> listarAvaliacoesPorResenha(
            @PathVariable Integer resenhaId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AvaliacaoDTO> avaliacoes = avaliacaoService.listarAvaliacaoPorResenha(resenhaId, page, size);
        String mensagem = avaliacoes.isEmpty() ? "Nenhuma avaliação encontrada" : "Avaliações encontradas com sucesso";
        return new ApiResponse<>(avaliacoes, mensagem, !avaliacoes.isEmpty(), getFormattedTimestamp());
    }

    @PutMapping("/{avaliacaoId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<AvaliacaoDTO> atualizarAvaliacao(
            @PathVariable Integer resenhaId,
            @PathVariable Integer avaliacaoId,
            @RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
        AvaliacaoDTO resultado = avaliacaoService.editarAvaliacao(resenhaId, avaliacaoId, avaliacaoDTO);
        return new ApiResponse<>(resultado, "Avaliação atualizada com sucesso.", true, getFormattedTimestamp());
    }

    @DeleteMapping("/{avaliacaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAvaliacao(@PathVariable Integer resenhaId, @PathVariable Integer avaliacaoId) {
        avaliacaoService.deletarAvaliacao(resenhaId, avaliacaoId);
    }

    @GetMapping("/perfil/{perfilId}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<AvaliacaoDTO>> listarAvaliacoesPorPerfil(
            @PathVariable Integer resenhaId,
            @PathVariable Integer perfilId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        Page<AvaliacaoDTO> resultado = avaliacaoService.listarAvaliacoesPorPerfil(perfilId, page, size);
        String mensagem = resultado.isEmpty() ? "Nenhuma avaliação encontrada para este perfil." : "Avaliações do perfil listadas com sucesso.";
        return new ApiResponse<>(resultado.isEmpty() ? null : resultado, mensagem, !resultado.isEmpty(), getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
