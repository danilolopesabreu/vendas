package br.com.hostelpro.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import br.com.hostelpro.entity.ProdutoEstabelecimento;

import java.util.List;

@Repository
public interface ProdutoEstabelecimentoRepository extends JpaRepository<ProdutoEstabelecimento, Integer> {
    List<ProdutoEstabelecimento> findByEstabelecimentoId(Integer estabelecimentoId);
}