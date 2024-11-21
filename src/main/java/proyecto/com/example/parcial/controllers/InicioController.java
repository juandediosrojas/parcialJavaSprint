package proyecto.com.example.parcial.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class InicioController {

    @GetMapping("/inicio")
    public String inicio() {
        // Devolver la vista 'layout' con el contenido de la página 'inicio'
        return "inicio";
    }
}
