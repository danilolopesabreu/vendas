package br.com.hostelpro.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hostelpro.dto.UsuarioDTO;
import br.com.hostelpro.entity.Usuario;
import br.com.hostelpro.mapper.UsuarioMapper;
import br.com.hostelpro.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/usuarios")
public class UsuarioController {

	@Autowired
    private UsuarioService service;
    
	@Autowired
	private UsuarioMapper mapper;

    @PostMapping
    public ResponseEntity<UsuarioDTO> criar(@Valid @RequestBody UsuarioDTO dto) {
        Usuario entidade = mapper.toEntity(dto);
        // senha: aqui usamos campo dto.senha -> em produção, hash na service
        entidade.setSenhaHash(dto.getSenhaHash());
        Usuario salvo = service.criar(entidade);
        return ResponseEntity.ok(mapper.toDTO(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UsuarioDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDTO(service.buscarPorId(id)));
    }

    @PostMapping("/email")
    public ResponseEntity<UsuarioDTO> getByEmail(@RequestBody UsuarioDTO usuarioDTO) {
    	return ResponseEntity.ok(mapper.toDTO(service.buscarPorEmailComRelacionamentos(usuarioDTO.getEmail())));
    }

    @GetMapping
    public ResponseEntity<List<UsuarioDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos().stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UsuarioDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody UsuarioDTO dto) {
        Usuario entidade = mapper.toEntity(dto);
        Usuario atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(mapper.toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
