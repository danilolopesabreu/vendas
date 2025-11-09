package br.com.hostelpro.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hostelpro.entity.TipoEstabelecimento;

@Repository
public interface TipoEstabelecimentoRepository extends JpaRepository<TipoEstabelecimento, Integer> {

    // Consulta adicional útil: buscar pelo nome
    Optional<TipoEstabelecimento> findByNome(String nome);

    // Consulta adicional: buscar por agrupador
    Optional<TipoEstabelecimento> findByAgrupador(String agrupador);
}