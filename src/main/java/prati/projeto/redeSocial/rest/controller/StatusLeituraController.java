package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.StatusLeituraDTO;
import prati.projeto.redeSocial.service.StatusLeituraService;

@RestController
@RequestMapping("/libris/status")
public class StatusLeituraController {

    @Autowired
    private StatusLeituraService statusLeituraService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StatusLeituraDTO criarStatus(@RequestBody @Valid StatusLeituraDTO dto){
        return statusLeituraService.salvarStatus(dto.getPerfilId(), dto.getLivroId(), dto.getStatus());
    }


    @PutMapping("/{id}")
    public StatusLeituraDTO mudarStatus(@PathVariable Integer id, @RequestBody @Valid StatusLeituraDTO dto){
        return statusLeituraService.mudarStatus(id, dto.getStatus());
    }
}
