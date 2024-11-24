package proyecto.com.example.parcial.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import proyecto.com.example.parcial.services.UsuarioService;

@Configuration
public class SecurityConfig {

    private final UsuarioService usuarioService;

    public SecurityConfig(UsuarioService usuarioService) {
        this.usuarioService = usuarioService;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(requests -> requests
                        .requestMatchers("/css/**", "/js/**", "/images/**").permitAll() // Permite acceso a archivos
                                                                                        // estáticos
                        .requestMatchers("/mantenimiento/**").hasRole("ADMIN")
                        .requestMatchers("/reportes/**").hasAnyRole("USER", "ADMIN")
                        .anyRequest().authenticated())
                .formLogin(login -> login
                        .loginPage("/login")
                        .defaultSuccessUrl("/inicio", true)
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/login?logout")
                        .permitAll())
                .exceptionHandling(handling -> handling
                        .accessDeniedHandler((request, response, accessDeniedException) -> {
                            response.sendRedirect("/inicio"); // Redirige a inicio en caso de 403
                        })
                        .authenticationEntryPoint((request, response, authException) -> {
                            response.sendRedirect("/login"); // Redirigir al login si no está autenticado
                        }));
        return http.build();
    }

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        return username -> {
            var usuario = usuarioService.findByUsername(username);
            if (usuario == null) {
                throw new UsernameNotFoundException("Usuario no encontrado");
            }
            return User.builder()
                    .username(usuario.getUsername())
                    .password(usuario.getPassword())
                    .roles(usuario.getRole())
                    .build();
        };
    }
}
