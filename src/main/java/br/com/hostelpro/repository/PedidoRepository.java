package br.com.hostelpro.repository;

import br.com.hostelpro.entity.Pedido;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface PedidoRepository extends JpaRepository<Pedido, Integer> {
    List<Pedido> findByEstabelecimentoId(Integer estabelecimentoId);
}
