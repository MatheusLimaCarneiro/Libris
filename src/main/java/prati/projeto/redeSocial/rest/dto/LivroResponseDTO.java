package prati.projeto.redeSocial.rest.dto;

import jakarta.persistence.Column;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

import java.time.LocalDate;

@Data
public class LivroResponseDTO {

    @NotBlank(message = "O Google ID é obrigatório")
    private String googleId;

    @NotBlank(message = "O título é obrigatório")
    private String titulo;

    private String subtitulo;

    private String autores;

    private String editora;

    @Size(max = 4096, message = "A sinopse deve ter no máximo 4096 caracteres")
    private String sinopse;

    private int numeroPaginas;

    private String isbn;

    private String idioma;

    private String categoria;

    @NotBlank(message = "A URL da capa é obrigatória")
    @URL(message = "A URL da capa deve ser uma URL válida")
    private String url_capa;

    @URL(message = "O link de compra deve ser uma URL válida")
    private String linkCompra;

    private String faixaEtaria;

    private LocalDate dataPublicacao;
}