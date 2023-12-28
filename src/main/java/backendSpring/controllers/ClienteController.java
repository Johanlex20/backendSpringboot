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


@RestController
@RequestMapping(value = "/api")
@CrossOrigin(origins = {"http://localhost:4200", "http://localhost:8080"})

public class ClienteController {
    @Autowired
    ClienteServices clienteServices;

    @GetMapping(value = "/clientes")
    public List<Cliente> findAll(){
        System.out.println("Resultados encontardos: ");
        return clienteServices.findAll();
    }

    /*
    @GetMapping(value = "/clientes/{id}")
    public Cliente findById(@PathVariable(value = "id") Long id){
        return clienteServices.findById(id);
    }
    */

    @GetMapping(value = "/clientes/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){

        Cliente cliente = null;
        Map<String,Object> response = new HashMap<>();

        try{
            cliente = clienteServices.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error: al realizar la consulta en la base de datos.");
            response.put("error",e.getMessage().concat(e.getMostSpecificCause().getMessage()));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null){
            response.put("mensaje","Usuario ID: ".concat(id.toString().concat(": ".concat("no existe en la base de datos."))));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.NOT_FOUND);
        }

        return new ResponseEntity<>(cliente,HttpStatus.OK);
    }

    /*
    @PostMapping(value = "/clientes")
    public Cliente save(@RequestBody Cliente cliente){
        System.out.println("Creado con éxito: "+ cliente.getNombre());
        return clienteServices.save(cliente);
    }
    */

    @PostMapping(value = "/clientes")
    public ResponseEntity<?> save(@RequestBody Cliente cliente){
        Cliente clientenuevo = null;
        Map<String,Object> response = new HashMap<>();

        try{
            clientenuevo = clienteServices.save(cliente);
        }catch (DataAccessException e){
            response.put("mensaje","Error: al crear el usuario en la base de datos");
            response.put("error",e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","Usuario creado con éxito");
        response.put("Usuario: ",clientenuevo.getNombre());
        return new ResponseEntity<Map<String,Object> >(response,HttpStatus.CREATED);
    }
    /*
    @DeleteMapping(value = "/clientes/{id}")
    public Boolean delete(@PathVariable Long id){
        System.out.println("Eliminado: "+clienteServices.findById(id).getNombre());
        return clienteServices.delete(id);
    }
    */


    @DeleteMapping(value = "/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id){
        Cliente clienteEliminado = clienteServices.findById(id);
        Map<String,Object> response = new HashMap<>();

        if (clienteEliminado == null){
            response.put("mensaje","Error: el usuario no exite en la base de datos");
            return new ResponseEntity<Map<String,Object> >(response,HttpStatus.NOT_FOUND);
        }

        try {
            if (clienteEliminado != null) {
                clienteServices.delete(id);
            }
        }catch (DataAccessException e){
            response.put("mensaje","Error: al eliminar al usuario ID: ".concat(id.toString().concat(" no existe en la base de datos")));
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","Usuiaro eliminado con exito.");
        return new ResponseEntity<>(response, HttpStatus.OK);
    }



    /*
    @PutMapping(value = "/clientes/{id}")
    public Cliente update(@PathVariable(value = "id") Long id,@RequestBody Cliente cliente){
        Cliente clienteActualizado = clienteServices.findById(id);
        if (clienteServices != null){
            clienteActualizado.setNombre(cliente.getNombre());
            clienteActualizado.setEmail(cliente.getEmail());
            clienteActualizado.setFecha(cliente.getFecha());
        }
        System.out.println("Actualizado con éxito");
        return clienteServices.save(clienteActualizado);
    }
    */

    @PutMapping(value = "/clientes/{id}")
    public ResponseEntity<?> update(@PathVariable(value = "id") Long id,@RequestBody Cliente cliente){
        Cliente clienteActualizado = clienteServices.findById(id);
        Cliente clienteModificado = null;
        Map<String,Object> response = new HashMap<>();

        if (clienteActualizado == null){
            response.put("mensaje","Error: al actualizar el usuario ID: ".concat(id.toString().concat(" no exite en la base de datos.")));
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }

        try{
            if (clienteServices != null){
                clienteActualizado.setNombre(cliente.getNombre());
                clienteActualizado.setEmail(cliente.getEmail());
                clienteActualizado.setFecha(cliente.getFecha());
            }
            clienteModificado = clienteServices.save(clienteActualizado);
        }catch (DataAccessException e){
            response.put("mensaje", "El usuario no se pudo actualizar en la base de datos.");
            response.put("error", e.getMessage().concat(": ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }


        response.put("mensaje", "Usuario actualizado con éxito");
        response.put("Usuario:",clienteModificado);
        return new ResponseEntity<Map<String,Object>>(response,HttpStatus.CREATED);
    }
}
