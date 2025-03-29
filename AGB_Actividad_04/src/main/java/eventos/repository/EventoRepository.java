package eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import eventos.entities.Evento;

public interface EventoRepository extends JpaRepository<Evento, Integer>{
	public List<Evento> findByTipoIdTipo(int idTipo);
	@Query("from Evento e where e.estado = 'ACEPTADO' and e.destacado='S'")
	public List<Evento> findByAceptadoAndDestacado();
	
	public List<Evento> findByEstado(String estado);
	
}
