package proyecto.com.example.parcial.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import proyecto.com.example.parcial.models.Trabajador;
import proyecto.com.example.parcial.services.TrabajadorService;

@Controller
@RequestMapping("/mantenimiento")
public class MantenimientoController {

    private final TrabajadorService trabajadorService;

    // Inyección del servicio a través del constructor
    public MantenimientoController(TrabajadorService trabajadorService) {
        this.trabajadorService = trabajadorService;
    }

    @GetMapping("/trabajadores")
    public String listarTrabajadores(Model model) {
        // Lógica para obtener lista de trabajadores
        model.addAttribute("trabajadores", trabajadorService.findAll());
        return "trabajadores";
    }

    @PostMapping("/trabajadores")
    public String guardarTrabajador(
            @RequestParam(required = false) Long id,
            @RequestParam String nombre,
            @RequestParam String email,
            @RequestParam String telefono,
            Model model) {

        Trabajador trabajador;

        if (id != null && id > 0) {
            trabajador = trabajadorService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
            trabajador.setNombre(nombre);
            trabajador.setEmail(email);
            trabajador.setTelefono(telefono);
        } else {
            trabajador = new Trabajador();
            trabajador.setNombre(nombre);
            trabajador.setEmail(email);
            trabajador.setTelefono(telefono);
        }

        trabajadorService.save(trabajador);

        return "redirect:/mantenimiento/trabajadores";
    }

    @GetMapping("/trabajadores/edit/{id}")
    public String editarTrabajador(@PathVariable Long id, Model model) {
        // Lógica para obtener trabajador por id
        Trabajador trabajador = trabajadorService.findById(id)
                .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
        if (trabajador != null) {
            model.addAttribute("trabajador", trabajador);
            return "editTrabajador";
        }            
        return "redirect:/mantenimiento/trabajadores";
    }

    @PostMapping("/trabajadores/delete/{id}")
    public String eliminarTrabajador(@PathVariable Long id) {
        // Lógica para eliminar trabajador por id
        trabajadorService.deleteById(id);
        return "redirect:/mantenimiento/trabajadores";
    }

    // @GetMapping("/servicios")
    // public String listarServicios(Model model) {
    // // Lógica para obtener lista de servicios
    // model.addAttribute("servicios", servicioService.findAll());
    // return "mantenimiento/servicios";
    // }

    // @GetMapping("/clientes")
    // public String listarClientes(Model model) {
    // // Lógica para obtener lista de clientes
    // model.addAttribute("clientes", clienteService.findAll());
    // return "mantenimiento/clientes";
    // }

    // @GetMapping("/tipo")
    // public String listarTipos(Model model) {
    // // Lógica para obtener lista de tipos
    // model.addAttribute("tipos", tipoService.findAll());
    // return "mantenimiento/tipo";
    // }

}
