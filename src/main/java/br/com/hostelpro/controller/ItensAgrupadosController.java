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

import br.com.hostelpro.dto.ItensAgrupadosDTO;
import br.com.hostelpro.entity.ItensAgrupados;
import br.com.hostelpro.mapper.ItensAgrupadosMapper;
import br.com.hostelpro.service.ItensAgrupadosService;

@RestController
@RequestMapping("/api/itens-agrupados")
public class ItensAgrupadosController {

    private final ItensAgrupadosService service;
    private final ItensAgrupadosMapper mapper;

    public ItensAgrupadosController(ItensAgrupadosService service, ItensAgrupadosMapper mapper) {
        this.service = service;
        this.mapper = mapper;
    }

    @GetMapping("/estabelecimento/{id}")
    public List<ItensAgrupadosDTO> listarPorEstabelecimento(@PathVariable Integer id) {
        return service.listarPorEstabelecimento(id)
                .stream()
                .map(mapper::toDTO)
                .collect(Collectors.toList());
    }

    @PostMapping
    public ItensAgrupadosDTO criar(@RequestBody ItensAgrupadosDTO dto) {
        ItensAgrupados entity = mapper.toEntity(dto);
        ItensAgrupados salvo = service.salvar(entity);
        return mapper.toDTO(salvo);
    }

    @PostMapping("/lista")
    public List<ItensAgrupadosDTO> criarByList(@RequestBody List<ItensAgrupadosDTO> dto) {
    	List<ItensAgrupados> entity = mapper.toEntityList(dto);
    	List<ItensAgrupados> salvo = service.salvarLista(entity);
    	return mapper.toDTOList(salvo);
    }

    @PutMapping("/{id}")
    public ItensAgrupadosDTO atualizar(@PathVariable Integer id, @RequestBody ItensAgrupadosDTO dto) {
        ItensAgrupados entity = mapper.toEntity(dto);
        ItensAgrupados atualizado = service.atualizar(id, entity);
        return mapper.toDTO(atualizado);
    }

    @DeleteMapping("/{id}")
    public void deletar(@PathVariable Integer id) {
        service.deletar(id);
    }

    @GetMapping("/{id}")
    public ItensAgrupadosDTO buscarPorId(@PathVariable Integer id) {
        ItensAgrupados entity = service.findById(id);
        return mapper.toDTO(entity);
    }
}
