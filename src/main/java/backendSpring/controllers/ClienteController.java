package backendSpring.controllers;
import backendSpring.models.Cliente;
import backendSpring.services.ClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})

public class ClienteController {
    @Autowired
    ClienteServices clienteServices;

    @GetMapping(value = "/clientes")
    public List<Cliente> findAll(){
        System.out.println("Resultados encontrados: ");
        return clienteServices.findAll();
    }

    @GetMapping(value = "/clientes/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){

        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try{
            cliente = clienteServices.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje", "Error al realizar la consulta en la base de datos!");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null){
            response.put("mensaje", "El cliente ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            return new ResponseEntity<Map<String, Object>>(response, HttpStatus.NOT_FOUND);
        }

        System.out.println("Cliente encontrado: "+ cliente.getNombre());
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    @PostMapping(value = "/clientes")
    public Cliente save(@RequestBody  Cliente cliente){
        System.out.printf("Cliente creado con exito: ");
        return this.clienteServices.save(cliente);
    }

    @DeleteMapping(value = "/clientes/{id}")
    public Boolean delete(@PathVariable(value = "id") Long id){
        Cliente clienteEliminado = clienteServices.findById(id);
        System.out.printf("Cliente Eliminado: " +clienteEliminado.getNombre());
        return clienteServices.delete(id);
    }

    @PutMapping(value = "/clientes/{id}")
    public Cliente upDate(@PathVariable(value = "id") Long id, @RequestBody Cliente cliente){
        Cliente clienteActualizado = clienteServices.findById(id);

        if (clienteActualizado != null){
            clienteActualizado.setNombre(cliente.getNombre());
            clienteActualizado.setApellido(cliente.getApellido());
            clienteActualizado.setEmail(cliente.getEmail());
            clienteActualizado.setTelefono(cliente.getTelefono());
            clienteActualizado.setFecha(cliente.getFecha());
            System.out.println("Cliente actualizado: "+cliente.getNombre());
        }
        return clienteServices.save(clienteActualizado);
    }


}
