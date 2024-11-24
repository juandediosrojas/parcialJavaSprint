package proyecto.com.example.parcial.controllers;


import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import proyecto.com.example.parcial.models.Usuario;
import proyecto.com.example.parcial.services.UsuarioService;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.ui.Model;


@Controller
@RequestMapping("/configuracion")
public class ConfiguracionController {

    private final UsuarioService usuarioService;

    public ConfiguracionController(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }
    
    @GetMapping
    public String mostrarConfiguracion(Model model) {
        String usuario = obtenerUsuarioLogeado();
        model.addAttribute("usuarios", usuarioService.findAll());	
        model.addAttribute("usuario", usuario);
        return "configuracion";
    }


    @RequestMapping("/cambiarPassword")
    public String cambiarPassword(String currentPassword, String newPassword, String confirmNewPassword, RedirectAttributes model) {
        String usuarioLogeado = obtenerUsuarioLogeado();
        Usuario usuario = usuarioService.findByUsername(usuarioLogeado);

        if (!newPassword.equals(confirmNewPassword)) {
            model.addFlashAttribute("error", "La nueva contraseña y la confirmación no coinciden.");
            return "redirect:/configuracion";
        }

        if (!usuarioService.checkPassword(usuario, currentPassword)) {
            model.addFlashAttribute("error", "La contraseña actual es incorrecta.");
            return "redirect:/configuracion";
        }

        usuarioService.updatePassword(usuario, newPassword);
        model.addFlashAttribute("mensaje", "Contraseña actualizada correctamente.");
        return "redirect:/configuracion";
    }



    @RequestMapping("/newUser")
    public String crearNuevoUsuario(String username, String password, String role, RedirectAttributes model) {
        Usuario nuevoUsuario = new Usuario();
        nuevoUsuario.setUsername(username);
        nuevoUsuario.setPassword(password);
        nuevoUsuario.setRole(role);

        usuarioService.save(nuevoUsuario);
        model.addFlashAttribute("mensaje", "Usuario creado correctamente.");
        return "redirect:/configuracion";
    }


    @RequestMapping("/changeRole")
    public String cambiarRol(String username, String newRole, RedirectAttributes  model) {
        Usuario usuario = usuarioService.findByUsername(username);

        if (usuario == null) {
            model.addFlashAttribute("error", "Usuario no encontrado.");
            return "redirect:/configuracion";  // Redirige a la página de configuración
        }

        usuario.setRole(newRole);
        usuarioService.save(usuario);
        model.addFlashAttribute("mensaje", "Rol actualizado correctamente.");
        return "redirect:/configuracion";  // Redirige a la página de configuración
    }
    
    public String obtenerUsuarioLogeado() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        System.out.println(authentication.getName());
        if (authentication != null && authentication.getAuthorities() != null) {
            String roles = authentication.getAuthorities().toString();
            System.out.println("Roles: " + roles);
        }
        return authentication.getName();
    }
}
