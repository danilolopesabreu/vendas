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

import br.com.hostelpro.dto.QuartoDTO;
import br.com.hostelpro.entity.Quarto;
import br.com.hostelpro.mapper.QuartoMapper;
import br.com.hostelpro.service.QuartoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/quartos")
public class QuartoController {

	@Autowired
    private QuartoService service;
    
	@Autowired
	private QuartoMapper mapper;

    @PostMapping
    public ResponseEntity<QuartoDTO> criar(@Valid @RequestBody QuartoDTO dto) {
        Quarto entidade = mapper.toEntity(dto);
        Quarto salvo = service.criar(entidade);
        return ResponseEntity.ok(mapper.toDTO(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<QuartoDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDTO(service.buscarPorId(id)));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<List<QuartoDTO>> listarPorEstabelecimento(@PathVariable Integer estabelecimentoId) {
        return ResponseEntity.ok(service.listarPorEstabelecimento(estabelecimentoId).stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<QuartoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody QuartoDTO dto) {
        Quarto entidade = mapper.toEntity(dto);
        Quarto atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(mapper.toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
