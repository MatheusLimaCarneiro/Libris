package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.AvaliacaoDTO;
import prati.projeto.redeSocial.service.AvaliacaoService;

import java.util.List;

@RestController
@RequestMapping("/libris/resenhas/{resenhaId}/avaliacoes")
public class AvaliacaoController {

    @Autowired
    private AvaliacaoService avaliacaoService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AvaliacaoDTO adicionarAvaliacao(
            @PathVariable Integer resenhaId,
            @RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
        return avaliacaoService.adicionarAvaliacao(resenhaId, avaliacaoDTO);
    }

    @GetMapping
    public List<AvaliacaoDTO> listarAvaliacoesPorResenha(@PathVariable Integer resenhaId) {
        return avaliacaoService.listarAvaliacaoPorResenha(resenhaId);
    }

    @PutMapping("/{avaliacaoId}")
    public AvaliacaoDTO atualizarAvaliacao(
            @PathVariable Integer avaliacaoId,
            @RequestBody @Valid AvaliacaoDTO avaliacaoDTO) {
        return avaliacaoService.editarAvaliacao(avaliacaoId, avaliacaoDTO);
    }

    @DeleteMapping("/{avaliacaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarAvaliacao(@PathVariable Integer avaliacaoId) {
        avaliacaoService.deletarAvaliacao(avaliacaoId);
    }

    @GetMapping("/perfil/{perfilId}")
    public List<AvaliacaoDTO> listarAvaliacoesPorPerfil(@PathVariable Integer perfilId) {
        return avaliacaoService.listarAvaliacoesPorPerfil(perfilId);
    }
}