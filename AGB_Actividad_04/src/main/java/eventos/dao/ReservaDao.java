package eventos.dao;

import java.util.List;

import eventos.entities.Evento;
import eventos.entities.Reserva;
import eventos.entities.Usuario;

public interface ReservaDao {
	
	List<Reserva> buscarPorUsuario(Usuario usuario);
	List<Reserva> buscarPorEvento(Evento evento);
	int crearReserva(Reserva reserva);
	int modificarReserva(Reserva reserva);
	int obtenerTotalReservasParaEvento(int idEvento);
	int eliminar (int idReserva);
	Reserva buscarUnaReserva(int idReserva);

}
