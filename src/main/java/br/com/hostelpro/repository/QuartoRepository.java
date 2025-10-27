package br.com.hostelpro.repository;

import br.com.hostelpro.entity.Quarto;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface QuartoRepository extends JpaRepository<Quarto, Integer> {
    List<Quarto> findByEstabelecimentoId(Integer estabelecimentoId);
}
