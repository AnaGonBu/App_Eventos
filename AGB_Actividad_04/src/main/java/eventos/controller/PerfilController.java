package eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eventos.dao.PerfilDao;

@Controller
@RequestMapping("/app/producto")
public class PerfilController {
	
	@Autowired
	public PerfilDao pdao;
	
    @GetMapping("/perfiles")
    public String usuarios(Model model) {
        model.addAttribute("perfiles", pdao.buscarTodos()); // Corrección del DAO
        return "perfiles"; // Asegúrate de que la vista "usuarios.html" existe en /templates
    }

}
