package eventos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entities.Perfil;
import eventos.repository.PerfilRepository;
@Repository
public class PerfilDaoImpl implements PerfilDao{
	
	@Autowired
	private PerfilRepository prepo;

	@Override
	public Perfil buscarUno(int idPerfil) {
		// TODO Auto-generated method stub
		return prepo.findById(idPerfil).orElse(null);
	}

	@Override
	public List<Perfil> buscarTodos() {
		
		return prepo.findAll();
	}

}
