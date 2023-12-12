package backendSpring.services;

import backendSpring.models.Cliente;
import backendSpring.repository.iClienteRespository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClienteServices implements  iClienteService{

    @Autowired
    iClienteRespository clienteRepository;

    @Override
    public List<Cliente> findAll() {
        return clienteRepository.findAll();
    }

    @Override
    public Cliente findById(Long id) {
        return clienteRepository.findById(id).orElse(null);
    }

    @Override
    public Cliente save(Cliente cliente) {
        return clienteRepository.save(cliente);
    }

    @Override
    public Boolean delete(Long id) {
        this.clienteRepository.deleteById(id);
        return true;
    }
}
