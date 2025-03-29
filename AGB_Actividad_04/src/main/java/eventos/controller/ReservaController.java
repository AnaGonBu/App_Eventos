package eventos.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import eventos.dao.EventoDao;
import eventos.dao.ReservaDao;
import eventos.dao.UsuarioDao;
import eventos.entities.Evento;
import eventos.entities.Reserva;
import eventos.entities.Usuario;
import jakarta.servlet.http.HttpSession;

@Controller
public class ReservaController {
	
	@Autowired
	private ReservaDao rdao;
	
	@Autowired
	private EventoDao edao;
	
	@Autowired
	private UsuarioDao udao;
	
	
	// Mostrar Mis Reservas
	@GetMapping ("/app/tipos/misReservas/{idUsuario}")
	public String mostrarReservas(@PathVariable String idUsuario, Model model ) {
		Usuario usu =udao.buscarPorUsername(idUsuario);
		List<Reserva> reservas;
		reservas= rdao.buscarPorUsuario(usu);
		model.addAttribute("reservas", reservas);
		
		return "MisReservas";
		
	}
	
	// Modificar Cantidad en Mis Reservas
	@PostMapping("/app/tipos/modCantidad/{idReserva}")
	public String modificarReserva(@PathVariable int idReserva, @RequestParam int cantidad, 
			HttpSession misesion,RedirectAttributes rett) {
		
	    Usuario usuario = (Usuario) misesion.getAttribute("usuario");
	    Reserva reserva = rdao.buscarUnaReserva(idReserva);
	    Evento evento = reserva.getEvento(); // No necesitas obtenerlo por separado


	    //  Validar que la nueva reserva no supere el máximo permitido por usuario (10)
	    if (cantidad < 1 || cantidad > 10) {
	    	rett.addFlashAttribute("mensaje", "No puedes reservar más de 10 plazas por reserva.");
	        return "redirect:/app/tipos/misReservas/" + usuario.getUsername();
	    }

	    // Validar que la reserva no supere el aforo disponible
	    int totalReservas = rdao.obtenerTotalReservasParaEvento(evento.getIdEvento()) - reserva.getCantidad();
	    int aforoDisponible = evento.getAforoMaximo() - totalReservas;

	    if (cantidad > aforoDisponible) {
	    	rett.addFlashAttribute("mensaje", "No hay suficiente espacio en el evento.");
	        return "redirect:/app/tipos/misReservas/" + usuario.getUsername();
	    }

	    // Actualizar la cantidad de la reserva
	    reserva.setCantidad(cantidad);
	    rdao.modificarReserva(reserva);

	    return "redirect:/app/tipos/misReservas/" + usuario.getUsername();
	}

	
	
	//CANCELAR en mis reservas
    @GetMapping("/app/tipos/cancelarReserva/{idReserva}")
    public String cancelarReserva(@PathVariable int idReserva,HttpSession session) {
         rdao.eliminar(idReserva);
         Usuario usuario = (Usuario) session.getAttribute("usuario");

        return "redirect:/app/tipos/misReservas/" + usuario.getUsername(); // Redirige a la lista de reservas
    }


	
    //Alta en detalle
	@GetMapping("/app/tipos/alta/{cantidad}/{IdEvento}")
	public String reservaAlta(@PathVariable int cantidad,@PathVariable int IdEvento, Model model) {
	    if (cantidad > 10) {
	        model.addAttribute("mensaje", "Sólo se permiten reservas de hasta 10 asistentes");
	        return "redirect:/DetalleEvento";
	    }

	    Evento evento = edao.buscarUno(IdEvento);
	    
	    List<Reserva> reservas = rdao.buscarPorEvento(evento);
	    model.addAttribute("reservas", reservas);

	    return "redirect:/DetalleEvento";
	}


	@PostMapping("/app/tipos/alta/{cantidad}/{idEvento}")
	public String reservarEvento(@RequestParam int cantidad, @PathVariable int idEvento, 
	                             HttpSession misesion, RedirectAttributes rett) {
	    

	    Usuario usuario = (Usuario) misesion.getAttribute("usuario");  
	    Evento evento = edao.buscarUno(idEvento);

	    //  Validar que la nueva reserva no supere el máximo permitido por usuario (10)
	    if (cantidad < 1 || cantidad > 10) {
	    	rett.addFlashAttribute("mensaje", "No puedes reservar más de 10 plazas por reserva.");
	        return "redirect:/public/verUno/" + idEvento;
	    }

	    // Validar que la reserva no supere el aforo disponible
	    int totalReservas = rdao.obtenerTotalReservasParaEvento(idEvento);
	    int aforoDisponible = evento.getAforoMaximo() - (totalReservas != 0 ? totalReservas : 0);

	    if (cantidad > aforoDisponible) {
	    	rett.addFlashAttribute("mensaje", "No hay suficiente espacio en el evento.");
	        return "redirect:/public/verUno/" + idEvento;
	    }

	    // Buscar si el usuario ya tiene una reserva en este evento 
	    List<Reserva> reservasUsuario = rdao.buscarPorUsuario(usuario);
	    Reserva reservaExistente = null;
	    for (Reserva r : reservasUsuario) {
	        if (r.getEvento().getIdEvento() == idEvento) {
	            reservaExistente = r;
	            break;
	        }
	    }

	    //  Si ya tiene reserva, sumar la nueva cantidad y validar el total
	    if (reservaExistente != null) {
	        int totalReservado = reservaExistente.getCantidad() + cantidad;

	        if (totalReservado > 10) {
	        	rett.addFlashAttribute("mensaje", "No puedes tener más de 10 plazas reservadas en total.");
	            return "redirect:/public/verUno/" + idEvento;
	        }

	        if (totalReservado > aforoDisponible) {
	        	rett.addFlashAttribute("mensaje", "No hay suficiente espacio en el evento.");
	            return "redirect:/public/verUno/" + idEvento;
	        }

	        // Actualizar la reserva existente
	        reservaExistente.setCantidad(totalReservado);
	        rdao.modificarReserva(reservaExistente);
	        rett.addFlashAttribute("mensaje", "Reserva actualizada con éxito.");
	    } else {
	        // Si es la primera reserva del usuario en este evento
	        Reserva nuevaReserva = new Reserva();
	        nuevaReserva.setUsuario(usuario);
	        nuevaReserva.setEvento(evento);
	        nuevaReserva.setCantidad(cantidad);
	        nuevaReserva.setPrecioVenta(evento.getPrecio());

	        rdao.crearReserva(nuevaReserva);
	        rett.addFlashAttribute("mensaje", "Reserva realizada con éxito.");
	    }

	    return "redirect:/public/verUno/" + idEvento;
	}





}
