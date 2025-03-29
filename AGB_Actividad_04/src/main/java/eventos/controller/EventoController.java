package eventos.controller;


import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;


import eventos.dao.EventoDao;
import eventos.dao.ReservaDao;
import eventos.dao.TipoDao;
import eventos.entities.Evento;
import eventos.entities.Reserva;
import eventos.entities.Usuario;
import jakarta.servlet.http.HttpSession;



@Controller
public class EventoController {
	
	@Autowired
	private EventoDao edao;
	
	@Autowired
	private TipoDao tdao;
	
	@Autowired
	private ReservaDao rdao;
	
	//alta
	//verDetalle	
	//eliminar
	//cancelar
	//modificar
	//buscar por tipo
	//buscar por estado
	
	
    //  MOSTRAR FORMULARIO DE ALTA
    @GetMapping("/app/producto/alta")
    public String mostrarFormularioAlta(Model model) {
        model.addAttribute("evento", new Evento()); // Se envía un objeto vacío para el formulario
        model.addAttribute("tipos", tdao.buscarTodos()); 
        return "AltaEvento"; // Nombre de la vista en /templates
    }

    //  PROCESAR ALTA DE EVENTO
    @PostMapping("/app/producto/alta")
    public String procesarAlta(@ModelAttribute Evento evento) {
    	
        try {
        	edao.alta(evento);
		} catch (Exception e) {
			// TODO Auto-generated catch block
					e.printStackTrace();
		}
        
        return "redirect:/"; // Redirige a la lista de eventos
    }
    
	//VER UNO, DETALLE
	@GetMapping("/public/verUno/{idEvento}")
	public String verUno(@PathVariable int idEvento, Model model) {
		
	    Evento evento = edao.buscarUno(idEvento);
	    int totalReservas = rdao.buscarPorEvento(evento).stream().mapToInt(Reserva::getCantidad).sum(); // Sumar reservas existentes
	    model.addAttribute("evento", evento);
	    model.addAttribute("totalReservas", totalReservas); // Pasamos el total de reservas a la vista
	    return "DetalleEvento";
		
	}
	
	// ELIMINAR
	@GetMapping("/app/producto/eliminar/{idEvento}")
	public String eliminarUno(@PathVariable int idEvento) {
		
		edao.eliminar2(idEvento);
		
		
		return "redirect:/";
		
	}

	//CANCELAR
    @GetMapping("/app/producto/cancelar/{idEvento}")
    public String cancelarEvento(@PathVariable int idEvento) {
        int resultado = edao.cancelarEvento(idEvento);
        if (resultado == 1) {
            System.out.println("Evento cancelado correctamente.");
        } else {
            System.out.println("Error al cancelar el evento.");
        }
        return "redirect:/"; // Redirige a la lista de eventos
    }
	

	//Recoger solicitud y mostrar formulario EDITAR
    @GetMapping("/app/producto/editar/{idEvento}")
    public String mostrarFormularioEdicion(@PathVariable int idEvento, Model model) {
        Evento evento = edao.buscarUno(idEvento);
        if (evento != null) {
            model.addAttribute("evento", evento);
            model.addAttribute("tipos", tdao.buscarTodos()); // Cargar tipos de evento
            return "EditarEvento"; // Vista del formulario
        }
        return "redirect:/app/producto/eventos"; // Si no existe, redirige a la lista, que no es muy probable,se selecciona pq aparece si id
    }

    // PROCESAR MODIFICACIÓN DEL EVENTO
    @PostMapping("/app/producto/editar")
    public String procesarEdicion(@ModelAttribute Evento evento) {
        int resultado = edao.modificar(evento);
        if (resultado == 1) {
            System.out.println("Evento modificado correctamente.");
        } else {
            System.out.println("Error al modificar el evento.");
        }
        return "redirect:/"; // Redirige al home después de la edición
    }
    
    //BUSCAR POR ESTADO
    @GetMapping("/app/producto/buscarPorEstado")
    public String buscarPorestado(@RequestParam(required = false, defaultValue = "") String estado, 
                                  Model model, HttpSession misesion) {
        Usuario usuario = (Usuario) misesion.getAttribute("usuario");

        List<Evento> eventosFiltrados;

        if (usuario == null) { 
            // Si es un invitado y NO ha seleccionado filtro, mostrar solo los aceptados y destacados
            eventosFiltrados = edao.buscarPorAceptadoYDestacado(); 
        } else if (estado.isEmpty()) { 
            // Si el usuario está autenticado pero no pone filtro, mostrar todos los eventos
            eventosFiltrados = edao.buscarPorestado(""); 
        } else { 
            // Si el usuario aplica un filtro, usamos la búsqueda normal
            eventosFiltrados = edao.buscarPorestado(estado);
        }

        model.addAttribute("eventos", eventosFiltrados);
        return "home";
    }

    
    //BUSCAR POR TIPO
    @GetMapping("/app/producto/buscarPorTipo")
    public String buscarPorTipo(@RequestParam int idTipo, Model model) {
        List<Evento> eventosFiltrados = edao.buscarPorTipo(idTipo);
        model.addAttribute("eventos", eventosFiltrados);
        return "home";
    }



	


}
