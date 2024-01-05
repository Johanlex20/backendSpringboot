package backendSpring.controllers;
import backendSpring.models.Cliente;
import backendSpring.services.ClienteServices;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


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

    @GetMapping(value = "/clientes/page/{page}")
    public Page<Cliente> findAll(@PathVariable Integer page){
        System.out.println("Resultados encontardos: ");
        Pageable pageable = PageRequest.of(page,4);
        return clienteServices.findAll(pageable);
    }

    //----------------------------------------------------------------METODO findId ----------------------------------------------------------------------------//
    /*
    @GetMapping(value = "/clientes/{id}")
    public Cliente findById(@PathVariable(value = "id") Long id){
        return clienteServices.findById(id);
    }
    */

    @GetMapping(value = "/clientes/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id") Long id){
        Cliente cliente = null;
        Map<String, Object> response = new HashMap<>();

        try {
            cliente = clienteServices.findById(id);
        }catch (DataAccessException e){
            response.put("mensaje","Error: Usuario ID: ".concat(id.toString().concat(" no encontrado en la base de datos!")));
            response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (cliente == null){
            response.put("mensaje","Error: Usuario no encontrado en la base de datos.");
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<Cliente>(cliente, HttpStatus.OK);
    }

    //-------------------------------------------------------------------CREAR---------------------------------------------------------------------------------//
    /*
    @PostMapping(value = "/clientes")
    public Cliente save(@RequestBody Cliente cliente){
        System.out.println("Creado con éxito: "+ cliente.getNombre());
        return clienteServices.save(cliente);
    }
    */

    @PostMapping(value = "/clientes")
    public ResponseEntity<?> save(@Valid @RequestBody  Cliente cliente, BindingResult result){
        Cliente newCliente = null;
        Map<String,Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err ->"El campo '"+ err.getField() +"' "+ err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors",errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }

        try{
            newCliente = clienteServices.save(cliente);
        }catch (DataAccessException e){
            response.put("mensaje","Error: no es posible crear en la base de datos!");
            response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }
        response.put("mensaje","Creado con éxito.");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    //-------------------------------------------------------------------DELETE---------------------------------------------------------------------------------//
    /*
    @DeleteMapping(value = "/clientes/{id}")
    public Boolean delete(@PathVariable Long id){
        System.out.println("Eliminado: "+clienteServices.findById(id).getNombre());
        return clienteServices.delete(id);
    }
    */
    @DeleteMapping(value = "/clientes/{id}")
    public ResponseEntity<?> delete(@PathVariable(value = "id") Long id){
        Cliente clienteEliminado =clienteServices.findById(id);
        Map<String, Object> response = new HashMap<>();

        if (clienteEliminado == null){
            response.put("mensaje","Error: no es posible eliminar en la base de datos!");
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.NOT_FOUND);
        }

        try {
            clienteServices.delete(id);
        }catch (DataAccessException e){
            response.put("mensaje","Usuario ID: ".concat(id.toString().concat(" no éxiste en la base de datos!")));
            response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String, Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","Usuario eliminado!");
        return new ResponseEntity<Map<String, Object>>(response,HttpStatus.OK);
    }

    //------------------------------------------------------------------UPDATE------------------------------------------------------------------------------------//

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
    public ResponseEntity<?> upDate(@Valid @RequestBody Cliente cliente, BindingResult result, @PathVariable(value = "id") Long id){
        Cliente clienteActualizado = clienteServices.findById(id);
        Map<String,Object> response = new HashMap<>();

        if (result.hasErrors()){
            List<String> errors = result.getFieldErrors()
                    .stream()
                    .map(err -> "El campo '"+ err.getField()+" '"+err.getDefaultMessage())
                    .collect(Collectors.toList());
            response.put("errors", errors);
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.BAD_REQUEST);
        }

        try{
            if (clienteActualizado != null){
                clienteActualizado.setNombre(cliente.getNombre());
                clienteActualizado.setApellido(cliente.getApellido());
                clienteActualizado.setEmail(cliente.getEmail());
                clienteActualizado.setTelefono(cliente.getTelefono());
                clienteActualizado.setFecha(cliente.getFecha());
                System.out.println("Cliente actualizado: "+cliente.getNombre());
                clienteServices.save(clienteActualizado);
            }else {
                response.put("mensaje","Error: el usuario no exite en la base de datos");
                return new ResponseEntity<Map<String,Object> >(response,HttpStatus.NOT_FOUND);
            }

        }catch (DataAccessException e){
            response.put("mensaje","Error: Usuario ID: ".concat(id.toString().concat(" no existe en la base de datos!")));
            response.put("error", e.getMessage().concat(" : ".concat(e.getMostSpecificCause().getMessage())));
            return new ResponseEntity<Map<String,Object>>(response,HttpStatus.INTERNAL_SERVER_ERROR);
        }

        response.put("mensaje","Usuario Actualizado!");
        return new ResponseEntity<Map<String,Object>>(response, HttpStatus.CREATED);
    }

    //------------------------------------------------------------------------------------------------------------------------------------------------------/
}
