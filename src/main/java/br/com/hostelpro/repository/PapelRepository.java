package br.com.hostelpro.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.hostelpro.entity.Papel;

@Repository
public interface PapelRepository extends JpaRepository<Papel, Integer> {
    
    // Consulta adicional (exemplo): buscar por nome
    Optional<Papel> findByNome(String nome);
}