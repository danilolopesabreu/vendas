package br.com.hostelpro.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.hostelpro.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

	Optional<Usuario> findByEmail(String email);

	@Query("""
	    SELECT u
		    FROM Usuario u
		    JOIN FETCH u.estabelecimento e
		    JOIN FETCH e.tipoEstabelecimento te
		    JOIN FETCH te.agrupador a
		    WHERE u.email = :email
		""")
	Optional<Usuario> findByEmailComRelacionamentos(@Param("email") String email);
}
