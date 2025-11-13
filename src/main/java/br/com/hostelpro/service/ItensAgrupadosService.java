package br.com.hostelpro.service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.hostelpro.entity.ItensAgrupados;
import br.com.hostelpro.repository.EstabelecimentoRepository;
import br.com.hostelpro.repository.ItensAgrupadosRepository;
import br.com.hostelpro.repository.TipoEstabelecimentoRepository;

@Service
public class ItensAgrupadosService {

	@Autowired
    private ItensAgrupadosRepository itensAgrupadosRepository;
	
	@Autowired
	private EstabelecimentoRepository estabelecimentoRepository;
	
	@Autowired
	private TipoEstabelecimentoRepository tipoEstabelecimentoRepository;

    public List<ItensAgrupados> listarPorEstabelecimento(Integer estabelecimentoId) {
        return itensAgrupadosRepository.findByEstabelecimentoId(estabelecimentoId);
    }

    @Transactional
    public ItensAgrupados salvar(ItensAgrupados itemAgrupado) {
    	
    	var estabelecimento = this.estabelecimentoRepository.findById(itemAgrupado.getEstabelecimento().getId()).get();
    	var tipoEstabelecimento = this.tipoEstabelecimentoRepository.findById(itemAgrupado.getTipoEstabelecimento().getId()).get();
    	
    	itemAgrupado.setEstabelecimento(estabelecimento);
    	itemAgrupado.setTipoEstabelecimento(tipoEstabelecimento);
    	
    	itemAgrupado = itensAgrupadosRepository.save(itemAgrupado);
        
        return itemAgrupado;
    }

    @Transactional
    public List<ItensAgrupados> salvarLista(List<ItensAgrupados> itensAgrupados) {
    	
   		var estabelecimento = this.estabelecimentoRepository.findById(itensAgrupados.get(0).getEstabelecimento().getId()).get();
   		var tipoEstabelecimento = this.tipoEstabelecimentoRepository.findById(itensAgrupados.get(0).getTipoEstabelecimento().getId()).get();
		
   		for (ItensAgrupados umItemAgrupado : itensAgrupados) {
			umItemAgrupado.setEstabelecimento(estabelecimento);
			umItemAgrupado.setTipoEstabelecimento(tipoEstabelecimento);
		}

   		itensAgrupados = itensAgrupadosRepository.saveAll(itensAgrupados);
    	
    	return itensAgrupados;    	
    }

    @Transactional
    public ItensAgrupados atualizar(Integer id, ItensAgrupados entityAtualizado) {
        ItensAgrupados existente = itensAgrupadosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
        existente.setNome(entityAtualizado.getNome());
        existente.setEstabelecimento(entityAtualizado.getEstabelecimento());
        existente.setTipoEstabelecimento(entityAtualizado.getTipoEstabelecimento());
        return itensAgrupadosRepository.save(existente);
    }

    @Transactional
    public void deletar(Integer id) {
        itensAgrupadosRepository.deleteById(id);
    }

    public ItensAgrupados findById(Integer id) {
        return itensAgrupadosRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item não encontrado"));
    }
}
