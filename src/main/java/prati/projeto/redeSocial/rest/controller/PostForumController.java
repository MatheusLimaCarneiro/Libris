package prati.projeto.redeSocial.rest.controller;

import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.rest.dto.PostForumRequestDTO;
import prati.projeto.redeSocial.rest.dto.PostForumResponseDTO;
import prati.projeto.redeSocial.rest.response.ServiceResponse;
import prati.projeto.redeSocial.service.PostForumService;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@RestController
@RequestMapping("/libris/posts")
public class PostForumController {

    @Autowired
    private PostForumService postForumService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ServiceResponse<PostForumResponseDTO> criarPost(@RequestBody @Valid PostForumRequestDTO dto) {
        PostForumResponseDTO postDTO = postForumService.criarPost(dto);
        return new ServiceResponse<>(postDTO, "Post criado com sucesso", true, getFormattedTimestamp());
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<PostForumResponseDTO> buscarPorId(@PathVariable Integer id) {
        PostForumResponseDTO postDTO = postForumService.buscarPorId(id);
        return new ServiceResponse<>(postDTO, "Post encontrado", true, getFormattedTimestamp());
    }

    @GetMapping("/listar")
    @ResponseStatus(HttpStatus.OK)
    public ServiceResponse<Page<PostForumResponseDTO>> listarTodos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Page<PostForumResponseDTO> posts = postForumService.listarTodos(page, size);
        String mensagem = posts.isEmpty() ? "Nenhum post encontrado" : "Posts encontrados";
        return new ServiceResponse<>(posts, mensagem, !posts.isEmpty(), getFormattedTimestamp());
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarPost(@PathVariable Integer id) {
        postForumService.deletarPost(id);
    }

    private String getFormattedTimestamp() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}