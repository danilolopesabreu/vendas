package br.com.hostelpro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hostelpro.entity.ProdutoEstabelecimento;

@Repository
public interface ProdutoEstabelecimentoRepository extends JpaRepository<ProdutoEstabelecimento, Integer> {
	
    List<ProdutoEstabelecimento> findByEstabelecimentoId(Integer estabelecimentoId);
    
    @EntityGraph(attributePaths = {})
    List<ProdutoEstabelecimento> findByEstabelecimentoIdOrderByQuantidadeVendidaDescNomeAsc(Integer estabelecimentoId);

}