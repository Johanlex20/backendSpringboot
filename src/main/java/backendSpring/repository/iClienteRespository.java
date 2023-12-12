package backendSpring.repository;

import backendSpring.models.Cliente;
import org.springframework.data.jpa.repository.JpaRepository;

public interface iClienteRespository extends JpaRepository<Cliente,Long>{
}
