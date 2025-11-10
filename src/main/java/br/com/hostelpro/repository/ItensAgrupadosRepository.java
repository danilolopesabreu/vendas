package br.com.hostelpro.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.hostelpro.entity.ItensAgrupados;

public interface ItensAgrupadosRepository extends JpaRepository<ItensAgrupados, Integer> {
    List<ItensAgrupados> findByEstabelecimentoId(Integer estabelecimentoId);
}
