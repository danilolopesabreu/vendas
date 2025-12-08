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

import br.com.hostelpro.dto.CategoriaProdutoDTO;
import br.com.hostelpro.entity.CategoriaProduto;
import br.com.hostelpro.mapper.CategoriaProdutoMapper;
import br.com.hostelpro.service.CategoriaProdutoService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categorias")
public class CategoriaProdutoController {

	@Autowired
    private CategoriaProdutoService service;
    
	@Autowired
	private CategoriaProdutoMapper mapper;

    @PostMapping
    public ResponseEntity<CategoriaProdutoDTO> criar(@Valid @RequestBody CategoriaProdutoDTO dto) {
        CategoriaProduto entidade = mapper.toEntity(dto);
        CategoriaProduto salvo = service.criar(entidade);
        return ResponseEntity.ok(mapper.toDTO(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CategoriaProdutoDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDTO(service.buscarPorId(id)));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<List<CategoriaProdutoDTO>> listarPorEstabelecimento(@PathVariable Integer estabelecimentoId) {
        return ResponseEntity.ok(service.listarPorEstabelecimento(estabelecimentoId).stream().map(mapper::toDTO).collect(Collectors.toList()));
    }
    
    @GetMapping("/estabelecimento/{estabelecimentoId}/principais")
    public ResponseEntity<List<CategoriaProdutoDTO>> listarCategoriasPrincipais(@PathVariable Integer estabelecimentoId) {
    	return ResponseEntity.ok(service.listarCategoriasPrincipais(estabelecimentoId).stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CategoriaProdutoDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody CategoriaProdutoDTO dto) {
        CategoriaProduto entidade = mapper.toEntity(dto);
        CategoriaProduto atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(mapper.toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
