package com.perfulandia_spa.cl.Perfulandia_SPA.controller;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Cliente;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.util.List;


@RestController


@RequestMapping("api/v1/clientes")
public class ClienteController {
    @Autowired
    private ClienteService clienteService;
    
    @Operation(summary = "Listar todos los clientes", description = "Obtiene una lista con todos los clientes registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida"),
        @ApiResponse(responseCode = "204", description = "No hay clientes registrados")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Cliente>> listar(){
        List<Cliente> clientes = clienteService.findAll();
        if(clientes.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(clientes);
    }

    @Operation(summary = "Crear un nuevo cliente", description = "Crea un cliente nuevo con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
    })

    @PostMapping("/guardar")
    public ResponseEntity<Cliente> guardar(@RequestBody Cliente cliente){
        Cliente clienteNuevo = clienteService.save(cliente);
        return ResponseEntity.status(HttpStatus.CREATED).body(clienteNuevo); 
    }
    @Operation(summary = "Buscar cliente por ID", description = "Obtiene un cliente específico mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })

    @GetMapping("/buscar")
    public ResponseEntity<Cliente> buscar(@PathVariable Integer id) {
        try{
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(cliente);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Actualizar cliente", description = "Actualiza un cliente existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "204", description = "No se encontró cliente para actualizar")
    })

    @PutMapping("/actualizar")
    public ResponseEntity<Cliente> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente){
        try{
            Cliente cliente2 = clienteService.findById(id);            
            cliente2.setId_cliente(id);
            cliente2.setCorreo(cliente.getCorreo());
            cliente2.setNombre(cliente.getNombre());
            clienteService.save(cliente2);
            return ResponseEntity.ok(cliente2);
        }catch(Exception e){
            return ResponseEntity.noContent().build();
        }
    }
    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })

    @DeleteMapping("/eliminar")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        try{
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
