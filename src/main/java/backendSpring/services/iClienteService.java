package backendSpring.services;

import backendSpring.models.Cliente;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface iClienteService {

    List<Cliente> findAll();

    Page<Cliente> findAll(Pageable pageable); //importar spring framedomain

    Cliente findById(Long id);

    Cliente save(Cliente cliente);

    Boolean delete(Long id);



}
