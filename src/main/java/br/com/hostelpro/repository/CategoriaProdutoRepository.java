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

	@Query("SELECT c FROM CategoriaProduto c WHERE c.estabelecimento.id = :estabelecimentoId AND c.categoriaPai IS NULL")
	List<CategoriaProduto> listarCategoriasPrincipais(@Param("estabelecimentoId") Integer estabelecimentoId);
	
    List<CategoriaProduto> findByEstabelecimentoIdAndCategoriaPaiIsNull(Integer estabelecimentoId);

    List<CategoriaProduto> findByEstabelecimentoIdAndSubcategoriasIsNull(Integer estabelecimentoId);

    @Query("""
    		SELECT new br.com.hostelpro.entity.CategoriaProduto(
    		    c.id,
    		    c.nome,

    		    CASE
				    WHEN EXISTS (
				        SELECT 1
				        FROM CategoriaProduto cp2
				        WHERE cp2.categoriaTemplate.id = (
				            SELECT cp.id
				            FROM CategoriaProduto cp
				            WHERE cp.id = c.categoriaTemplate.id
				        )
				    )
				    THEN true
				    ELSE false
				END,

    		    CASE
    		        WHEN EXISTS (
    		            SELECT 1
    		            FROM ItemPedido ip
    		            JOIN ip.produtoEstabelecimento pe
    		            WHERE pe.categoria.id = c.id
    		              AND pe.estabelecimento.id = :estabelecimentoId
    		        )
    		        THEN true ELSE false
    		    END
    		)
    		FROM CategoriaProduto c
    		WHERE
			    c.estabelecimento.id = :estabelecimentoId
			    OR (
			        c.estabelecimento.id = 1
			        AND NOT EXISTS (
			            SELECT 1
			            FROM CategoriaProduto cp
			            WHERE cp.estabelecimento.id = :estabelecimentoId
			              AND cp.categoriaTemplate.id = c.id
			        )
			    )
    		ORDER BY c.nome
    		""")
	List<CategoriaProduto> listarCategoriasParaEstabelecimento(@Param("estabelecimentoId") Integer estabelecimentoId);

}
