package eventos.controller;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.dao.EventoDao;
import eventos.dao.PerfilDao;
import eventos.dao.TipoDao;
import eventos.dao.UsuarioDao;
import eventos.entities.Evento;
import eventos.entities.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
public class HomeController {
	@Autowired
	private UsuarioDao udao;
	
	@Autowired
	private EventoDao edao;
	
	@Autowired
	private PerfilDao pdao;
	
	@Autowired
	private TipoDao tdao;
	
	@GetMapping("/inicioSesion")
	public String inicioSesion(Authentication aut, HttpSession misesion) {
		System.out.println("pasa por login2");
		
		String username = aut.getName();
		
		Usuario usuario=udao.buscarPorUsername(username);
		usuario.setPassword(null);
		
		misesion.setAttribute("usuario", usuario);
		
		
		
		return "forward:/home";
		
	}
	
	@GetMapping({"", "/", "/home"})
	public String home(
	    @RequestParam(required = false) String estado,
	    @RequestParam(required = false) Integer idTipo,
	    Model model) {

	    List<Evento> eventos;

	    if (estado != null && idTipo != null) {
	        // Filtrar por estado y tipo
	        eventos = edao.buscarPorestado(estado)
	                     .stream()
	                     .filter(evento -> evento.getTipo().getIdTipo() == idTipo)
	                     .collect(Collectors.toList());
	    } else if (estado != null) {
	        // Filtrar solo por estado
	        eventos = edao.buscarPorestado(estado);
	    } else if (idTipo != null) {
	        // Filtrar solo por tipo
	        eventos = edao.buscarPorTipo(idTipo);
	    } else {
	        // Mostrar eventos destacados por defecto
	        eventos = edao.buscarPorAceptadoYDestacado();
	    }

	    model.addAttribute("eventos", eventos);
	    model.addAttribute("tipos", tdao.buscarTodos());

	    return "home";
	}
	
	@GetMapping("/login")
	public String login() {
		System.out.println("pasa por login2");
			
	    return "FormLogin"; // Redirige a login.html
	}
	
	
	@GetMapping("/login-error")
	public String loginError(Model model) {
		System.out.println("pasa por aqui");
		model.addAttribute("mensaje", "Invalid username or password.");
        return "FormLogin";
	}
	
	
	
	@GetMapping("/registro")
	public String mostrarRegisratee() {
		return "Registrate";
	}
	
	@PostMapping("/registro")
	public String procRegistro(Usuario usuario, RedirectAttributes ratt, Model model) {
		
		usuario.setEnabled(1);
		usuario.setFechaRegistro(new Date());
		usuario.setPerfiles(List.of(pdao.buscarUno(2)));
	 	usuario.setPassword("{noop}"+usuario.getPassword());
	 	
		
		if (udao.altaUsuario(usuario)) {
			return "redirect:/login";
			
		}else {
			ratt.addFlashAttribute("mensaje", "Este usuario ya existe");     
			return "redirect:/registro";
		}
	}
		

			
	}
	



