package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.constraints.Positive;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.PerfilResumidoDTO;
import prati.projeto.redeSocial.rest.response.ApiResponse;
import prati.projeto.redeSocial.service.RelacionamentoSeguidoresService;

import java.time.LocalDateTime;
import java.util.Set;

@RestController
@RequestMapping("/libris/relacionamentos")
public class RelacionamentoSeguidoresController {

    @Autowired
    private RelacionamentoSeguidoresService relacionamentoService;

    @PostMapping("/seguir/{seguidorId}/{seguidoId}")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<ApiResponse<Void>> seguirPerfil(
            @PathVariable @Positive(message = "ID do seguidor deve ser positivo") Integer seguidorId,
            @PathVariable @Positive(message = "ID do seguido deve ser positivo") Integer seguidoId) {
        relacionamentoService.seguirPerfil(seguidorId, seguidoId);
        ApiResponse<Void> response = new ApiResponse<>(null, "Perfil seguido com sucesso", true, LocalDateTime.now());
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @DeleteMapping("/deixar-de-seguir/{seguidorId}/{seguidoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deixarDeSeguir(
            @PathVariable @Positive(message = "ID do seguidor deve ser positivo") Integer seguidorId,
            @PathVariable @Positive(message = "ID do seguido deve ser positivo") Integer seguidoId) {
        relacionamentoService.deixarDeSeguir(seguidorId, seguidoId);
    }

    @GetMapping("/esta-seguindo/{seguidorId}/{seguidoId}")
    public ResponseEntity<ApiResponse<Boolean>> estaSeguindo(
            @PathVariable Integer seguidorId,
            @PathVariable Integer seguidoId) {
        boolean resultado = relacionamentoService.estaSeguindo(seguidorId, seguidoId);
        ApiResponse<Boolean> response = new ApiResponse<>(resultado, "Verificação de seguimento", true, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seguidores/{perfilId}")
    public ResponseEntity<ApiResponse<Set<PerfilResumidoDTO>>> buscarSeguidores(@PathVariable Integer perfilId) {
        Set<PerfilResumidoDTO> seguidores = relacionamentoService.buscarSeguidores(perfilId);
        ApiResponse<Set<PerfilResumidoDTO>> response = new ApiResponse<>(seguidores, "Seguidores encontrados", true, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/seguindo/{perfilId}")
    public ResponseEntity<ApiResponse<Set<PerfilResumidoDTO>>> buscarSeguindo(@PathVariable Integer perfilId) {
        Set<PerfilResumidoDTO> seguindo = relacionamentoService.buscarSeguindo(perfilId);
        ApiResponse<Set<PerfilResumidoDTO>> response = new ApiResponse<>(seguindo, "Seguindo encontrados", true, LocalDateTime.now());
        return ResponseEntity.ok(response);
    }
}
