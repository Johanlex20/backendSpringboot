package backendSpring.services;

import backendSpring.models.Cliente;

import java.util.List;

public interface iClienteService {

    List<Cliente> findAll();

    Cliente findById(Long id);

    Cliente save(Cliente cliente);

    Boolean delete(Long id);



}
