package proyecto.com.example.parcial.services;

import org.springframework.stereotype.Service;
import proyecto.com.example.parcial.repositories.ServicioRepository;
import proyecto.com.example.parcial.models.Servicio;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class ServicioService {

    private final ServicioRepository servicioRepository;

    public ServicioService(ServicioRepository servicioRepository) {
        this.servicioRepository = servicioRepository;
    }

    public List<Servicio> findAll() {
        return servicioRepository.findAll() != null ? servicioRepository.findAll() : Collections.emptyList();
    }

    public Servicio save(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    public Optional<Servicio> findById(Long id) {
        return servicioRepository.findById(id);
        }

    public void deleteById(Long id) {
        servicioRepository.deleteById(id);
    }
}
