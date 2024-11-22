package proyecto.com.example.parcial.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.ui.Model;

import proyecto.com.example.parcial.models.Servicio;
import proyecto.com.example.parcial.models.Trabajador;
import proyecto.com.example.parcial.services.TrabajadorService;
import proyecto.com.example.parcial.services.ServicioService;

@Controller
@RequestMapping("/mantenimiento")
public class MantenimientoController {

    private final TrabajadorService trabajadorService;
    private final ServicioService servicioService;

    public MantenimientoController(TrabajadorService trabajadorService, ServicioService servicioService) {
        this.trabajadorService = trabajadorService;
        this.servicioService = servicioService;
    }

    // Listar trabajadores
    @GetMapping("/trabajadores")
    public String listarTrabajadores(Model model) {
        // Lógica para obtener lista de trabajadores
        model.addAttribute("trabajadores", trabajadorService.findAll());
        return "trabajadores";
    }

    // guardar trabajador
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

    // editar trabajdor, mas que todo lo busca para luego enviar a guardar, es como un buscar por id solamente
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

    // eliminar trabajador
    @PostMapping("/trabajadores/delete/{id}")
    public String eliminarTrabajador(@PathVariable Long id) {
        // Lógica para eliminar trabajador por id
        trabajadorService.deleteById(id);
        return "redirect:/mantenimiento/trabajadores";
    }

    // Listar  servicios
    @GetMapping("/servicios")
    public String listarServicios(Model model) {
    // Lógica para obtener lista de servicios
    model.addAttribute("servicios", servicioService.findAll());
    return "servicios";
    }

    // guardar SERVICIO
    @PostMapping("/servicios")
    public String guardarServicio(
            @RequestParam(required = false) Long id,
            @RequestParam String nombre,
            @RequestParam String descripcion,
            @RequestParam String precio,
            Model model) {

        Servicio servicio;

        if (id != null && id > 0) {
            servicio = servicioService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Trabajador no encontrado"));
            servicio.setNombre(nombre);
            servicio.setDescripcion(descripcion);
            servicio.setPrecio(precio);
        } else {
            servicio = new Servicio();
            servicio.setNombre(nombre);
            servicio.setDescripcion(descripcion);
            servicio.setPrecio(precio);
        }

        servicioService.save(servicio);

        return "redirect:/mantenimiento/servicios";
    }

    // editar SERVICIO, mas que todo lo busca para luego enviar a guardar, es como un buscar por id solamente
    @GetMapping("/servicios/edit/{id}")
    public String editarServicio(@PathVariable Long id, Model model) {
        // Lógica para obtener servicoi por id
        Servicio servicio = servicioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado"));
        if (servicio != null) {
            model.addAttribute("servicio", servicio);
            System.out.println(servicio.getNombre());
            System.out.println(servicio.getDescripcion());
            System.out.println(servicio.getPrecio());
            return "editServicio";
        }            
        return "redirect:/mantenimiento/servicios";
    }

     // eliminar trabajador
     @PostMapping("/servicios/delete/{id}")
     public String eliminarServicio(@PathVariable Long id) {
    // Lógica para eliminar trabajador por id
    servicioService.deleteById(id);
    return "redirect:/mantenimiento/servicios";
    }




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
