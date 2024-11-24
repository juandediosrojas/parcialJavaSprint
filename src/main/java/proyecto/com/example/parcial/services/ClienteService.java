package proyecto.com.example.parcial.services;

import org.springframework.stereotype.Service;
import proyecto.com.example.parcial.repositories.ClienteRepository;
import proyecto.com.example.parcial.models.Cliente;

import java.util.Collections;
import java.util.List;
import java.util.Optional;


@Service
public class ClienteService {
    
        private final ClienteRepository clienteRepository;
    
        public ClienteService(ClienteRepository clienteRepository) {
            this.clienteRepository = clienteRepository;
        }
    
        public List<Cliente> findAll() {
            return clienteRepository.findAll() != null ? clienteRepository.findAll() : Collections.emptyList();
        }
    
        public Cliente save(Cliente cliente) {
            return clienteRepository.save(cliente);
        }
    
        public Optional<Cliente> findById(Long id) {
            return clienteRepository.findById(id);
            }
    
        public void deleteById(Long id) {
            clienteRepository.deleteById(id);
        }
}
