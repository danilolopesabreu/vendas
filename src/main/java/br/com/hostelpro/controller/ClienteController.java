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

import br.com.hostelpro.dto.ClienteDTO;
import br.com.hostelpro.entity.Cliente;
import br.com.hostelpro.mapper.ClienteMapper;
import br.com.hostelpro.service.ClienteService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/clientes")
public class ClienteController {

	@Autowired
    private ClienteService service;
    
	@Autowired
	private ClienteMapper mapper;

    @PostMapping
    public ResponseEntity<ClienteDTO> criar(@Valid @RequestBody ClienteDTO dto) {
        Cliente entidade = mapper.toEntity(dto);
        Cliente salvo = service.criar(entidade);
        return ResponseEntity.ok(mapper.toDTO(salvo));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ClienteDTO> getById(@PathVariable Integer id) {
        return ResponseEntity.ok(mapper.toDTO(service.buscarPorId(id)));
    }

    @GetMapping("/estabelecimento/{estabelecimentoId}")
    public ResponseEntity<List<ClienteDTO>> listarPorEstabelecimento(@PathVariable Integer estabelecimentoId) {
        return ResponseEntity.ok(service.listarPorEstabelecimento(estabelecimentoId).stream().map(mapper::toDTO).collect(Collectors.toList()));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ClienteDTO> atualizar(@PathVariable Integer id, @Valid @RequestBody ClienteDTO dto) {
        Cliente entidade = mapper.toEntity(dto);
        Cliente atualizado = service.atualizar(id, entidade);
        return ResponseEntity.ok(mapper.toDTO(atualizado));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        service.deletar(id);
        return ResponseEntity.noContent().build();
    }
}
