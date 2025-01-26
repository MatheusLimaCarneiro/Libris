package prati.projeto.redeSocial.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UsuarioResumidoDTO {
    private String username;
    private String email;
}
