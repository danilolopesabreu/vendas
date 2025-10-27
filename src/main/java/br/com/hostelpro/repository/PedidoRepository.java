package br.com.hostelpro.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.hostelpro.entity.Pedido;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
	List<Pedido> findByEstabelecimentoId(Integer estabelecimentoId);

	@Query("""
	    SELECT DISTINCT p FROM Pedido p
	    JOIN FETCH p.itens ip
	    JOIN FETCH ip.produto prod
	    LEFT JOIN FETCH prod.categoriaProduto
	    WHERE p.estabelecimento.id = :estabelecimentoId
	      AND (:numeroQuarto IS NULL OR p.quarto.numero = :numeroQuarto)
			""")
	List<Pedido> findByEstabelecimentoAndOptionalQuartoWithProdutos(@Param("estabelecimentoId") Integer estabelecimentoId, @Param("numeroQuarto") String numeroQuarto);

}
