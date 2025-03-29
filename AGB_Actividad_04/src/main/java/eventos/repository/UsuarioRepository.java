package eventos.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entities.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, String>{
	
	Usuario findByUsername(String username);
	 List<Usuario> findAll();

}
