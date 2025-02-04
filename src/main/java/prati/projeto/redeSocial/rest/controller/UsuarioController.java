package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.modal.entity.Usuario;
import prati.projeto.redeSocial.rest.dto.UsuarioResumidoDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.UsuarioService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @GetMapping("{email}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UsuarioResumidoDTO> getUsuarioByEmail(@PathVariable String email) {
        UsuarioResumidoDTO usuarioResumido = usuarioService.getUsuarioByEmail(email);
        return new ApiResponse<>(usuarioResumido, "Usuário encontrado", true, getFormattedTimestamp());
    }

    @DeleteMapping("{email}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteUsuario(@PathVariable String email) {
        usuarioService.deleteUsuario(email);
    }

    @PutMapping("{email}")
    @ResponseStatus(HttpStatus.OK)
    public ApiResponse<UsuarioResumidoDTO> updateUsuario(@PathVariable String email,
                                                         @RequestBody @Valid Usuario usuario) {
        usuarioService.updateUsuario(email, usuario);
        UsuarioResumidoDTO usuarioResumido = new UsuarioResumidoDTO(usuario.getUsername(), usuario.getEmail());
        return new ApiResponse<>(usuarioResumido, "Usuário atualizado com sucesso", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}
