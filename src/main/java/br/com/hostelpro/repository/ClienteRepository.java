package br.com.hostelpro.repository;

import br.com.hostelpro.entity.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ClienteRepository extends JpaRepository<Cliente, Integer> {
    List<Cliente> findByEstabelecimentoId(Integer estabelecimentoId);
}
