package prati.projeto.redeSocial.rest.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PerfilResumidoDTO {
    private Integer id;
    private String urlPerfil;
    private String resumoBio;
    private String username;
}