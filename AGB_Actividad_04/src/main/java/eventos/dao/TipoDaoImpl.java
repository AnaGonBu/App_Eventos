package eventos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entities.Tipo;
import eventos.repository.TipoRepository;

@Repository
public class TipoDaoImpl implements TipoDao{
	
	@Autowired
	public TipoRepository trepo;

	@Override
	public List<Tipo> buscarTodos() {
		// TODO Auto-generated method stub
		return trepo.findAll();
	}
	
	

}
