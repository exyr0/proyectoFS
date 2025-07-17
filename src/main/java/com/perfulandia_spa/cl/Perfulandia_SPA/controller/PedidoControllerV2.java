package com.perfulandia_spa.cl.Perfulandia_SPA.controller;

import com.perfulandia_spa.cl.Perfulandia_SPA.assemblers.PedidoModelAssembler;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Pedido;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.CollectionModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/pedidos")
public class PedidoControllerV2 {

    @Autowired
    private PedidoService pedidoService;

    @Autowired
    private PedidoModelAssembler pedidoAssembler;

    @Operation(summary = "Listar todos los pedidos", description = "Obtiene una lista con todos los pedidos")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida")
    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Pedido>>> listar() {
        List<Pedido> pedidos = pedidoService.findAll();
        List<EntityModel<Pedido>> pedidosModel = pedidos.stream()
                .map(pedidoAssembler::toModel)
                .toList();

        return ResponseEntity.ok(
            CollectionModel.of(pedidosModel,
                linkTo(methodOn(PedidoControllerV2.class).listar()).withSelfRel()
            )
        );
    }

    @Operation(summary = "Buscar pedido por ID", description = "Obtiene un pedido específico mediante su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @GetMapping("{id}/buscar")
    public ResponseEntity<EntityModel<Pedido>> buscar(@PathVariable Long id) {
        try {
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedidoAssembler.toModel(pedido));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido con los datos proporcionados")
    @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente")
    @PostMapping("/crear")
    public ResponseEntity<EntityModel<Pedido>> crear(@RequestBody Pedido pedido) {
        Pedido nuevo = pedidoService.save(pedido);
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(pedidoAssembler.toModel(nuevo));
    }

    @Operation(summary = "Actualizar un pedido existente", description = "Actualiza el pedido identificado por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "Pedido actualizado"),
        @ApiResponse(responseCode = "204", description = "No hay contenido para actualizar")
    })
    @PutMapping("{id}/actualizar")
    public ResponseEntity<EntityModel<Pedido>> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        try {
            Pedido pedido2 = pedidoService.findById(id);
            pedido2.setId_pedido(id);
            pedido2.setFecha_pedido(pedido.getFecha_pedido());
            pedido2.setEstado_reserva(pedido.getEstado_reserva());
            pedidoService.save(pedido2);
            return ResponseEntity.ok(pedidoAssembler.toModel(pedido2));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Eliminar un pedido", description = "Elimina el pedido identificado por su ID")
    @ApiResponses({
        @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })
    @DeleteMapping("{id}/eliminar")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
