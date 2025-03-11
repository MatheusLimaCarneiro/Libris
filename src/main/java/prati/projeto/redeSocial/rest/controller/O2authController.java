package prati.projeto.redeSocial.rest.controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.TokenDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.security.JwtService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@AllArgsConstructor
@RestController
@RequestMapping("/libris/auth")
public class O2authController {

    private final JwtService jwtService;
    private static final Logger logger = LoggerFactory.getLogger(O2authController.class);

    @GetMapping("/oauth2/success")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<TokenDTO> oauth2Success( @RequestParam String token,
                                                    @RequestParam String refreshToken) {
        logger.info("Token recebido no endpoint /oauth2/success: " + token);

        if (token == null || token.isEmpty() || refreshToken == null || refreshToken.isEmpty()) {
            logger.error("Token ausente ou vazio.");
            return new ServiceResponse<>(null, "Token ausente ou vazio", false, getFormattedTimestamp());
        }

        if (!jwtService.tokenValido(token)) {
            logger.error("Token inválido: " + token);
            return new ServiceResponse<>(null, "Token inválido", false, getFormattedTimestamp());
        }

        TokenDTO tokenDTO = new TokenDTO();
        tokenDTO.setToken(token);
        tokenDTO.setRefreshToken(refreshToken);
        tokenDTO.setLogin(jwtService.obterLoginUsuario(token));
        return new ServiceResponse<>(tokenDTO, "Login com OAuth2 bem-sucedido", true, getFormattedTimestamp());
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}