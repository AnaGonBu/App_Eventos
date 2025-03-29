package eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entities.Perfil;


public interface PerfilRepository extends JpaRepository<Perfil, Integer> {
	
	public List<Perfil> findAll();

}
