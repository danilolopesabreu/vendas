package br.com.hostelpro.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.hostelpro.dto.TipoEstabelecimentoDTO;
import br.com.hostelpro.entity.TipoEstabelecimento;
import br.com.hostelpro.mapper.TipoEstabelecimentoMapper;
import br.com.hostelpro.service.TipoEstabelecimentoService;

@RestController
@RequestMapping("/tipos-estabelecimento")
public class TipoEstabelecimentoController {

    private final TipoEstabelecimentoService tipoEstabelecimentoService;
    private final TipoEstabelecimentoMapper tipoEstabelecimentoMapper;

    public TipoEstabelecimentoController(TipoEstabelecimentoService tipoEstabelecimentoService,
                                         TipoEstabelecimentoMapper tipoEstabelecimentoMapper) {
        this.tipoEstabelecimentoService = tipoEstabelecimentoService;
        this.tipoEstabelecimentoMapper = tipoEstabelecimentoMapper;
    }

    @GetMapping
    public ResponseEntity<List<TipoEstabelecimentoDTO>> listarTodos() {
        List<TipoEstabelecimento> entidades = tipoEstabelecimentoService.listarTodos();
        return ResponseEntity.ok(tipoEstabelecimentoMapper.toDTOList(entidades));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TipoEstabelecimentoDTO> buscarPorId(@PathVariable Integer id) {
        return tipoEstabelecimentoService.buscarPorId(id)
                .map(tipo -> ResponseEntity.ok(tipoEstabelecimentoMapper.toDTO(tipo)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<TipoEstabelecimentoDTO> criar(@RequestBody TipoEstabelecimentoDTO dto) {
        TipoEstabelecimento entity = tipoEstabelecimentoMapper.toEntity(dto);
        TipoEstabelecimento salvo = tipoEstabelecimentoService.salvar(entity);
        return ResponseEntity.ok(tipoEstabelecimentoMapper.toDTO(salvo));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TipoEstabelecimentoDTO> atualizar(@PathVariable Integer id,
                                                            @RequestBody TipoEstabelecimentoDTO dto) {
        return tipoEstabelecimentoService.buscarPorId(id)
                .map(existente -> {
                    existente.setNome(dto.getNome());
                    TipoEstabelecimento atualizado = tipoEstabelecimentoService.salvar(existente);
                    return ResponseEntity.ok(tipoEstabelecimentoMapper.toDTO(atualizado));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletar(@PathVariable Integer id) {
        tipoEstabelecimentoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}