package com.perfulandia_spa.cl.Perfulandia_SPA.controller;

import com.perfulandia_spa.cl.Perfulandia_SPA.assemblers.ClienteModelAssembler;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Cliente;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("api/v2/clientes")
public class ClienteControllerV2 {

    @Autowired
    private ClienteService clienteService;

    @Autowired
    private ClienteModelAssembler clienteAssembler;

    @Operation(summary = "Listar todos los clientes", description = "Obtiene una lista con todos los clientes registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de clientes obtenida"),
        @ApiResponse(responseCode = "204", description = "No hay clientes registrados")
    })
    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Cliente>>> listar() {
        List<Cliente> clientes = clienteService.findAll();
        if (clientes.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<Cliente>> clientesModel = clientes.stream()
            .map(clienteAssembler::toModel)
            .toList();

        return ResponseEntity.ok(
            CollectionModel.of(clientesModel,
                linkTo(methodOn(ClienteController.class).listar()).withSelfRel()
            )
        );
    }

    @Operation(summary = "Crear un nuevo cliente", description = "Crea un cliente nuevo con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Cliente creado exitosamente")
    })
    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<Cliente>> guardar(@RequestBody Cliente cliente) {
        Cliente clienteNuevo = clienteService.save(cliente);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(clienteAssembler.toModel(clienteNuevo));
    }

    @Operation(summary = "Buscar cliente por ID", description = "Obtiene un cliente específico mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente encontrado"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @GetMapping("{id}/buscar")
    public ResponseEntity<EntityModel<Cliente>> buscar(@PathVariable Integer id) {
        try {
            Cliente cliente = clienteService.findById(id);
            return ResponseEntity.ok(clienteAssembler.toModel(cliente));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar cliente", description = "Actualiza un cliente existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Cliente actualizado exitosamente"),
        @ApiResponse(responseCode = "204", description = "No se encontró cliente para actualizar")
    })
    @PutMapping("{id}/actualizar")
    public ResponseEntity<EntityModel<Cliente>> actualizar(@PathVariable Integer id, @RequestBody Cliente cliente) {
        try {
            Cliente clienteExistente = clienteService.findById(id);
            clienteExistente.setId_cliente(id);
            clienteExistente.setCorreo(cliente.getCorreo());
            clienteExistente.setNombre(cliente.getNombre());
            clienteService.save(clienteExistente);
            return ResponseEntity.ok(clienteAssembler.toModel(clienteExistente));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Eliminar cliente", description = "Elimina un cliente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Cliente eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @DeleteMapping("{id}/eliminar")
    public ResponseEntity<?> eliminar(@PathVariable Long id) {
        try {
            clienteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
