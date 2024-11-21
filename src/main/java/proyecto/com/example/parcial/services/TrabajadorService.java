package proyecto.com.example.parcial.services;

import org.springframework.stereotype.Service;

import proyecto.com.example.parcial.repositories.TrabajadorRepository;
import proyecto.com.example.parcial.models.Trabajador;
import java.util.List;
import java.util.Optional;

@Service
public class TrabajadorService {

    private final TrabajadorRepository trabajadorRepository;

    public TrabajadorService(TrabajadorRepository trabajadorRepository) {
        this.trabajadorRepository = trabajadorRepository;
    }

    public List<Trabajador> findAll() {
        return trabajadorRepository.findAll();
    }

    public Trabajador save(Trabajador trabajador) {
        return trabajadorRepository.save(trabajador);
    }

    public Optional<Trabajador> findById(Long id) {
        return trabajadorRepository.findById(id);
        }

    public void deleteById(Long id) {
        trabajadorRepository.deleteById(id);
    }

}
