package eventos.dao;

import java.util.List;

import eventos.entities.Perfil;


public interface PerfilDao {
	
	Perfil buscarUno(int idPerfil);
	List<Perfil> buscarTodos();

}
