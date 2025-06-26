package com.perfulandia_spa.cl.Perfulandia_SPA.controller;


import com.perfulandia_spa.cl.Perfulandia_SPA.model.Pedido;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.PedidoService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/api/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService pedidoService;


    @Operation(summary = "Listar todos los pedidos", description = "Obtiene una lista con todos los pedidos")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de pedidos obtenida")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Pedido>> listar() {
        List<Pedido> pedidos = pedidoService.findAll();
        return ResponseEntity.ok(pedidos);
    }

    @Operation(summary = "Buscar pedido por ID", description = "Obtiene un pedido específico mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido encontrado"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })

    @GetMapping("/buscar")
    public ResponseEntity<Pedido> buscar(@PathVariable Long id) {
        try{
            Pedido pedido = pedidoService.findById(id);
            return ResponseEntity.ok(pedido);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    @Operation(summary = "Crear un nuevo pedido", description = "Crea un nuevo pedido con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Pedido creado exitosamente")
    })

    @PostMapping("/crear")
    public ResponseEntity<Pedido> crear(@RequestBody Pedido pedido) {
        Pedido nuevo = pedidoService.save(pedido);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevo);
    }
    @Operation(summary = "Actualizar un pedido existente", description = "Actualiza el pedido identificado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Pedido actualizado"),
        @ApiResponse(responseCode = "204", description = "No hay contenido para actualizar")
    })

    @PutMapping("/actualizar")
    public ResponseEntity<Pedido> actualizar(@PathVariable Integer id, @RequestBody Pedido pedido) {
        try{
            Pedido pedido2 = pedidoService.findById(id);            
            pedido2.setId_pedido(id);
            pedido2.setFecha_pedido(pedido.getFecha_pedido());
            pedido2.setEstado_reserva(pedido.getEstado_reserva());
            pedidoService.save(pedido2);
            return ResponseEntity.ok(pedido2);
        }catch(Exception e){
            return ResponseEntity.noContent().build();
        }
    }
    @Operation(summary = "Eliminar un pedido", description = "Elimina el pedido identificado por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Pedido eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Pedido no encontrado")
    })

    @DeleteMapping("/eliminar")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try{
            pedidoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch(Exception e){
            return ResponseEntity.notFound().build();
        }
}
}