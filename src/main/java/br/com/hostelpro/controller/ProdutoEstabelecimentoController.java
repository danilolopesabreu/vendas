package br.com.hostelpro.controller;

import org.springframework.web.bind.annotation.*;

import br.com.hostelpro.dto.ProdutoEstabelecimentoDTO;
import br.com.hostelpro.service.ProdutoEstabelecimentoService;

import java.util.List;

@RestController
@RequestMapping("/api/produtos-estabelecimento")
public class ProdutoEstabelecimentoController {

    private final ProdutoEstabelecimentoService service;

    public ProdutoEstabelecimentoController(ProdutoEstabelecimentoService service) {
        this.service = service;
    }

    @GetMapping("/estabelecimento/{id}")
    public List<ProdutoEstabelecimentoDTO> listarPorEstabelecimento(@PathVariable Integer id) {
        return service.listarPorEstabelecimento(id);
    }

    @PostMapping
    public ProdutoEstabelecimentoDTO criar(@RequestBody ProdutoEstabelecimentoDTO dto) {
        return service.salvar(dto);
    }

    @PutMapping("/{id}")
    public ProdutoEstabelecimentoDTO atualizar(@PathVariable Integer id, @RequestBody ProdutoEstabelecimentoDTO dto) {
        return service.atualizar(id, dto);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }
}