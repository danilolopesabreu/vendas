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

import br.com.hostelpro.dto.EstabelecimentoDTO;
import br.com.hostelpro.entity.Estabelecimento;
import br.com.hostelpro.mapper.EstabelecimentoMapper;
import br.com.hostelpro.service.EstabelecimentoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/estabelecimentos")
public class EstabelecimentoController {

	@Autowired
    private EstabelecimentoService service;
    
	@Autowired
	private EstabelecimentoMapper mapper;

    @PostMapping
    public ResponseEntity<EstabelecimentoDTO> criar(@Valid @RequestBody EstabelecimentoDTO dto) {
        Estabelecimento entidade = mapper.toEntity(dto);
        Estabelecimento salvo = service.criar(entidade);
        return ResponseEntity.ok(mapper.toDTO(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<EstabelecimentoDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDTO(service.buscarPorId(id)));
    }

    @GetMapping
    public ResponseEntity<List<EstabelecimentoDTO>> listar() {
        return ResponseEntity.ok(service.listarTodos().stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<EstabelecimentoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody EstabelecimentoDTO dto) {
        Estabelecimento entidade = mapper.toEntity(dto);
        Estabelecimento atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(mapper.toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
