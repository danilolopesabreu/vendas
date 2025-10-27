package br.com.hostelpro.repository;

import br.com.hostelpro.entity.CategoriaProduto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoriaProdutoRepository extends JpaRepository<CategoriaProduto, Integer> {
    List<CategoriaProduto> findByEstabelecimentoId(Integer estabelecimentoId);
}
