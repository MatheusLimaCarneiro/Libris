package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.RespostaDTO;
import prati.projeto.redeSocial.service.ComentarioRespostaService;

import java.util.List;

@RestController
@RequestMapping("/libris/comentarios/{comentarioId}/respostas")
public class ComentarioRespostaController {

    @Autowired
    private ComentarioRespostaService respostaService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RespostaDTO adicionarResposta(
            @PathVariable Integer comentarioId,
            @RequestBody @Valid RespostaDTO respostaDTO) {
        return respostaService.adicionarResposta(comentarioId, respostaDTO);
    }

    @GetMapping
    public List<RespostaDTO> listarRespostas(@PathVariable Integer comentarioId) {
        return respostaService.listarRespostasPorComentario(comentarioId);
    }

    @PutMapping("/{respostaId}")
    public RespostaDTO atualizarResposta(
            @PathVariable Integer respostaId,
            @RequestBody @Valid RespostaDTO respostaDTO) {
        return respostaService.atualizarResposta(respostaId, respostaDTO);
    }

    @DeleteMapping("/{respostaId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarResposta(@PathVariable Integer respostaId) {
        respostaService.deletarResposta(respostaId);
    }
}
