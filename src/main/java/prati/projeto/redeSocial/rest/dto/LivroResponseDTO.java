package prati.projeto.redeSocial.rest.dto;

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

    @NotBlank(message = "Os autores são obrigatórios")
    private String autores;

    @NotBlank(message = "A editora é obrigatória")
    private String editora;

    @Size(max = 200, message = "A sinopse deve ter no máximo 200 caracteres")
    private String sinopse;

    @NotNull(message = "O número de páginas é obrigatório")
    @Min(value = 1, message = "O número de páginas deve ser maior que 0")
    private int numeroPaginas;

    @NotBlank(message = "O ISBN é obrigatório")
    @Size(max = 13, message = "O ISBN deve ter entre 10 e 13 caracteres")
    @Pattern(regexp = "\\d{10}|\\d{13}", message = "O ISBN deve conter apenas números")
    private String isbn;

    @NotBlank(message = "O idioma é obrigatório")
    private String idioma;

    @NotBlank(message = "A categoria é obrigatória")
    private String categoria;

    @NotBlank(message = "A URL da capa é obrigatória")
    @URL(message = "A URL da capa deve ser uma URL válida")
    private String url_capa;

    @NotBlank(message = "O link de compra é obrigatório")
    @URL(message = "O link de compra deve ser uma URL válida")
    private String linkCompra;

    @NotNull(message = "A data de publicação é obrigatória")
    private LocalDate dataPublicacao;
}