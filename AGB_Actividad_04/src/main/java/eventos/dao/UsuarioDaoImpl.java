package eventos.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import eventos.entities.Usuario;
import eventos.repository.UsuarioRepository;

@Repository
public class UsuarioDaoImpl implements UsuarioDao{
	
	@Autowired
	private UsuarioRepository usuarioRepository;
	
	@Override
	public Usuario buscarPorUsername(String username) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByUsername(username);
	}

	@Override
	public boolean altaUsuario(Usuario usuario) {
		try {
			usuarioRepository.save(usuario);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			
		}
		return false;
	}

	@Override
	public List<Usuario> buscarTodos() {
		// TODO Auto-generated method stub
		return usuarioRepository.findAll();
	}

	

	

}
