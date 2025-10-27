package br.com.hostelpro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.hostelpro.entity.CategoriaProduto;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Integer> {
	
    List<CategoriaProduto> findByEstabelecimentoId(Integer estabelecimentoId);

	@Query("SELECT c FROM CategoriaProduto c LEFT JOIN FETCH c.subcategorias WHERE c.estabelecimento.id = :estabelecimentoId AND c.categoriaPai IS NULL")
	List<CategoriaProduto> findHierarquiaPorEstabelecimento(@Param("estabelecimentoId") Integer estabelecimentoId);

}
