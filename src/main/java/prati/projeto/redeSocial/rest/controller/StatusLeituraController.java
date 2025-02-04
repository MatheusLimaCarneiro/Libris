package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.StatusLeituraDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.StatusLeituraService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/status")
public class StatusLeituraController {

    @Autowired
    private StatusLeituraService statusLeituraService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ApiResponse<StatusLeituraDTO> criarStatus(@RequestBody @Valid StatusLeituraDTO dto) {
        StatusLeituraDTO statusCriado = statusLeituraService.salvarStatus(dto.getPerfilId(), dto.getLivroId(), dto.getStatus());
        return new ApiResponse<>(statusCriado, "Status de leitura criado com sucesso", true, getFormattedTimestamp());
    }

    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<StatusLeituraDTO> mudarStatus(@PathVariable Integer id, @RequestBody @Valid StatusLeituraDTO dto) {
        StatusLeituraDTO statusAtualizado = statusLeituraService.mudarStatus(id, dto.getStatus());
        return new ApiResponse<>(statusAtualizado, "Status de leitura atualizado com sucesso", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
