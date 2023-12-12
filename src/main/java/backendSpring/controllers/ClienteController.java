package backendSpring.controllers;
import backendSpring.models.Cliente;
import backendSpring.services.ClienteServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "/api")

public class ClienteController {
    @Autowired
    ClienteServices clienteServices;

    @GetMapping(value = "/clientes")
    public List<Cliente> findAll(){
        System.out.println("Resultados encontrados: ");
        return clienteServices.findAll();
    }

    @GetMapping(value = "/clientes/{id}")
    public Cliente findById(@PathVariable(value = "id") Long id){
        Cliente clienteEncontrado = new Cliente();
        clienteEncontrado = clienteServices.findById(id);
        System.out.println("Cliente encontrado: "+ clienteEncontrado.getNombre());
        return this.clienteServices.findById(id);
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


}
