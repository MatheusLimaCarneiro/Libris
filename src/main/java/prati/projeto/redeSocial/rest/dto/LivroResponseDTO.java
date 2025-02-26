package prati.projeto.redeSocial.rest.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class LivroResponseDTO {
    private String googleId;
    private String titulo;
    private String subtitulo;
    private String autores;
    private String editora;
    private String sinopse;
    private int numeroPaginas;
    private String isbn;
    private String idioma;
    private String categoria;
    private String url_capa;
    private String linkCompra;
    private LocalDate dataPublicacao;
}
