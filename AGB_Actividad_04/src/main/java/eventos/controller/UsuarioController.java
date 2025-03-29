package eventos.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import eventos.dao.UsuarioDao;

@Controller
@RequestMapping("/app/producto")
public class UsuarioController {
    
    @Autowired
    private UsuarioDao udao;

    @GetMapping("/usuarios")
    public String usuarios(Model model) {
        model.addAttribute("usuarios", udao.buscarTodos()); // Corrección del DAO
        return "usuarios"; // Asegúrate de que la vista "usuarios.html" existe en /templates
    }
}
