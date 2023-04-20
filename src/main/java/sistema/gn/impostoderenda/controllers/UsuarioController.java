package sistema.gn.impostoderenda.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import sistema.gn.impostoderenda.DTOs.UsuarioGetDto;
import sistema.gn.impostoderenda.DTOs.UsuarioRequestDto;
import sistema.gn.impostoderenda.entities.Usuario;
import sistema.gn.impostoderenda.services.UsuarioService;

@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @PostMapping
    public ResponseEntity<Usuario> save(@RequestBody UsuarioRequestDto usuarioRequestDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(usuarioService.save(usuarioRequestDto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioGetDto> consultarResultado(@PathVariable Long id) {
        return ResponseEntity.status(HttpStatus.OK).body(usuarioService.consultarResultado(id));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> delete(@PathVariable Long id) {
        usuarioService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body("DELETADO COM SUCESSO");
    }
}
