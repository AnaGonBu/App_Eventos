package eventos.dao;

import java.util.List;

import eventos.entities.Evento;

public interface EventoDao {
	
    List<Evento> buscarPorEstado(String estado);
	List<Evento> buscarPorAceptadoYDestacado();
	List<Evento> buscarPorTipo(int idTipo);
	Evento buscarUno(int idEvento);
	List<Evento> buscarPorestado(String estado);
	int eliminar(int idEvento);
	void eliminar2(int idEvento);
	int cancelarEvento(int idEvento);
	int alta(Evento evento);
	int modificar(Evento evento);
	

}
