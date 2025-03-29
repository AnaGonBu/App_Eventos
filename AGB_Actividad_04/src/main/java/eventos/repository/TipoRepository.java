package eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entities.Tipo;




public interface TipoRepository extends JpaRepository<Tipo, Integer>{
	
	public List<Tipo> findAll();

}
