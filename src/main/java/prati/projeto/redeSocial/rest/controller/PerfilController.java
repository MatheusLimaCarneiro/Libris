package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Perfil;
import prati.projeto.redeSocial.rest.dto.PerfilDTO;
import prati.projeto.redeSocial.rest.dto.PerfilResumidoDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.PerfilService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/perfil")
public class PerfilController {

    @Autowired
    private PerfilService perfilService;

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PerfilDTO> getPerfilById(@PathVariable Integer id) {
        PerfilDTO perfil = perfilService.getPerfilById(id);
        return new ApiResponse<>(perfil, "Perfil encontrado", true, getFormattedTimestamp());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<PerfilDTO> savePerfil(@RequestBody @Valid Perfil perfil) {
        PerfilDTO perfilDTO = perfilService.savePerfil(perfil);
        return new ApiResponse<>(perfilDTO, "Perfil salvo com sucesso", true, getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletePerfil(@PathVariable Integer id) {
        perfilService.deletePerfil(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Void> updatePerfil(@PathVariable Integer id,
                                          @RequestBody @Valid Perfil perfil) {
        perfilService.updatePerfil(id, perfil);
        return new ApiResponse<>(null, "Perfil atualizado com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<Page<PerfilResumidoDTO>> getAllPerfil(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PerfilResumidoDTO> perfis = perfilService.listarPerfil(page, size);
        String mensagem = perfis.isEmpty() ? "Nenhum perfil encontrado" : "Perfis encontrados";

        return new ApiResponse<>(perfis, mensagem, !perfis.isEmpty(), getFormattedTimestamp());
    }

    @GetMapping("/buscar/{username}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<PerfilResumidoDTO> buscarPorUsername(@PathVariable String username) {
        PerfilResumidoDTO perfilResumidoDTO = perfilService.buscarPorUsername(username);
        String mensagem = perfilResumidoDTO != null ? "Perfil encontrado" : "Perfil não encontrado";
        return new ApiResponse<>(perfilResumidoDTO, mensagem, perfilResumidoDTO != null, getFormattedTimestamp());
    }


    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
