package eventos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entities.Evento;
import eventos.repository.EventoRepository;

@Repository
public class EventoDaoImpl implements EventoDao{
	
	
	@Autowired
	private EventoRepository erepo;
	
	

	@Override
	public List<Evento> buscarPorAceptadoYDestacado() {
		
		return erepo.findByAceptadoAndDestacado();
	}

	@Override
	public List<Evento> buscarPorTipo(int idTipo) {
		if(idTipo != 0) {
			return erepo.findByTipoIdTipo(idTipo);
		}
		
		return erepo.findAll();
	}

	@Override
	public Evento buscarUno(int idEvento) {
		
		return erepo.findById(idEvento).orElse(null);
	}
	
	@Override
	public List<Evento> buscarPorestado(String estado) {
		if(estado != "" ) {
			return erepo.findByEstado(estado);
		}

		return erepo.findAll();
	}
	
    @Override
    public int alta(Evento evento) {
        try {
            erepo.save(evento);
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0;
    }




	@Override
	public int eliminar(int idEvento) {
		try {
			if(erepo.existsById(idEvento)) {
			erepo.deleteById(idEvento);
			return 1;
			}	
		}catch (Exception e) {
			e.printStackTrace();
			
		}return 0;
	}

	@Override
	public void eliminar2(int idEvento) {
		try {
			if(erepo.existsById(idEvento)) {
			erepo.deleteById(idEvento);

			}	
		}catch (Exception e) {
			e.printStackTrace();
			
		}
		
	}

	@Override
	public int cancelarEvento(int idEvento) {
        try {
            Evento evento = erepo.findById(idEvento).orElse(null);
            if (evento != null) {
                evento.setEstado("CANCELADO");
                erepo.save(evento); // Guardamos el cambio en la base de datos
                return 1; // Indica éxito
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Indica fallo
    }

	@Override
	public List<Evento> buscarPorEstado(String estado) {
		if(estado != null) {
			return erepo.findByEstado(estado);
		}
		return erepo.findAll();
	}

	@Override
	public int modificar(Evento evento) {
        try {
            if (erepo.existsById(evento.getIdEvento())) {
                erepo.save(evento); // Actualiza el evento con los nuevos datos
                return 1; // Éxito
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0; // Fallo
    }
}
