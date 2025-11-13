package br.com.hostelpro.controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.hostelpro.dto.ProdutoEstabelecimentoDTO;
import br.com.hostelpro.entity.ProdutoEstabelecimento;
import br.com.hostelpro.mapper.ProdutoEstabelecimentoMapper;
import br.com.hostelpro.service.ProdutoEstabelecimentoService;

@RestController
@RequestMapping("/api/produtos-estabelecimento")
public class ProdutoEstabelecimentoController {

    private final ProdutoEstabelecimentoService service;
    private final ProdutoEstabelecimentoMapper mapper;

    public ProdutoEstabelecimentoController(ProdutoEstabelecimentoService service,
                                            ProdutoEstabelecimentoMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/estabelecimento/{id}")
    public List<ProdutoEstabelecimentoDTO> listarPorEstabelecimento(@PathVariable Integer id) {
        return service.listarProdutosMaisVendidosPorEstabelecimento(id)
                      .stream()
                      .map(mapper::toDTO)
                      .collect(Collectors.toList());
    }

    @GetMapping("/estabelecimento/{id}/maisvendidos")
    public List<ProdutoEstabelecimentoDTO> listarProdutosMaisVendidosPorEstabelecimento(@PathVariable Integer id) {
    	return service.listarProdutosMaisVendidosPorEstabelecimento(id)
    			.stream()
    			.map(mapper::toDTO)
    			.collect(Collectors.toList());
    }

    @PostMapping
    public ProdutoEstabelecimentoDTO criar(@RequestBody ProdutoEstabelecimentoDTO dto) {
        ProdutoEstabelecimento entity = mapper.toEntity(dto);
        ProdutoEstabelecimento salvo = service.salvar(entity);
        return mapper.toDTO(salvo);
    }

    @PutMapping("/{id}")
    public ProdutoEstabelecimentoDTO atualizar(@PathVariable Integer id,
                                               @RequestBody ProdutoEstabelecimentoDTO dto) {
        ProdutoEstabelecimento entity = mapper.toEntity(dto);
        ProdutoEstabelecimento atualizado = service.atualizar(id, entity);
        return mapper.toDTO(atualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}
