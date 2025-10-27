package br.com.hostelpro.repository;

import br.com.hostelpro.entity.Produto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProdutoRepository extends JpaRepository<Produto, Integer> {
    List<Produto> findByEstabelecimentoId(Integer estabelecimentoId);
}
