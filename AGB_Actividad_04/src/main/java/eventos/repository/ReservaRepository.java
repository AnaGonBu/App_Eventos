package eventos.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import eventos.entities.Evento;
import eventos.entities.Reserva;
import eventos.entities.Usuario;

public interface ReservaRepository extends JpaRepository<Reserva, Integer>{
	
	List<Reserva> findByUsuario(Usuario usuario);
	List<Reserva> findByEvento(Evento evento);

}
