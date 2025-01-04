package prati.projeto.redeSocial.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import prati.projeto.redeSocial.dto.ComentarioDTO;
import prati.projeto.redeSocial.modal.entity.Comentario;
import prati.projeto.redeSocial.service.ComentarioService;

import java.util.List;

@RestController
@RequestMapping("/libris/comentarios")
public class ComentarioController {

    @Autowired
    private ComentarioService comentarioService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Integer save(@RequestBody ComentarioDTO dto){
        Comentario comentario = comentarioService.salvar(dto);
        return comentario.getId();
    }

    @GetMapping("/{id}")
    public ComentarioDTO buscarPorId(@PathVariable Integer id){
        return comentarioService.buscarPorId(id);
    }

    @GetMapping
    public List<ComentarioDTO> listarTodos() {
        return comentarioService.listarTodos();
    }
}
