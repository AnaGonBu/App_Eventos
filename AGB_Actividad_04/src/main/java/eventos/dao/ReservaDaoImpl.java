package eventos.dao;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entities.Evento;
import eventos.entities.Reserva;
import eventos.entities.Usuario;
import eventos.repository.ReservaRepository;

@Repository
public class ReservaDaoImpl implements ReservaDao {
	
	@Autowired
	private ReservaRepository rrepo;


	@Override
	public List<Reserva> buscarPorUsuario(Usuario usuario) {
	 
	    return rrepo.findByUsuario(usuario);
	}

	@Override
	public List<Reserva> buscarPorEvento(Evento evento) {
	    
	    return rrepo.findByEvento(evento);
	}


	@Override
	public int crearReserva(Reserva reserva) {
		try {
			rrepo.save(reserva);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return 0;
	}


	@Override
	public int modificarReserva(Reserva reserva) {
		try {
			rrepo.save(reserva);
			return 1;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return 0;
		
	}


	@Override
	public int obtenerTotalReservasParaEvento(int idEvento) {
		 Evento evento = new Evento(); 
		    evento.setIdEvento(idEvento);

		List<Reserva> reservas = rrepo.findByEvento(evento); // Busca todas las reservas del evento
		return (reservas != null) ? reservas.stream().mapToInt(Reserva::getCantidad).sum() : 0; //devuelvo 0 en vez de un error
	}

	@Override
	public int eliminar(int idReserva) {
			try {
				if(rrepo.existsById(idReserva)) {
					rrepo.deleteById(idReserva);
				return 1;
				}	
			}catch (Exception e) {
				e.printStackTrace();
				
			}return 0;
		}

	@Override
	public Reserva buscarUnaReserva(int idReserva) {
	    try {
	        return rrepo.findById(idReserva).orElse(null); 
	    } catch (Exception e) {
	        e.printStackTrace();
	    }
	    return null;
	}

}
