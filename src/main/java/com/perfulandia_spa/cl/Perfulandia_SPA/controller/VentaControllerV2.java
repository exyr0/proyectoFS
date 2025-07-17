package com.perfulandia_spa.cl.Perfulandia_SPA.controller;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Venta;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.VentaService;
import com.perfulandia_spa.RegistrarVentaDTO;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ClienteRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.CarritoRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.VentaRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.assemblers.VentaModelAssembler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/ventas")
public class VentaControllerV2 {

    @Autowired
    private VentaService ventaService;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private VentaModelAssembler ventaAssembler;

    @Operation(summary = "Listar todas las ventas", description = "Obtiene una lista con todas las ventas registradas")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Lista de ventas encontrada"),
            @ApiResponse(responseCode = "204", description = "No existen ventas registradas")
    })
    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Venta>>> listar() {
        List<Venta> ventas = ventaService.findAll();
        if (ventas.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<Venta>> ventasModel = ventas.stream()
                .map(ventaAssembler::toModel)
                .toList();

        return ResponseEntity.ok(
                CollectionModel.of(ventasModel,
                        linkTo(methodOn(VentaControllerV2.class).listar()).withSelfRel())
        );
    }


    @Operation(summary = "Registrar una venta desde carrito y cliente", description = "Registra una venta basada en un carrito y cliente existentes")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Venta registrada exitosamente"),
            @ApiResponse(responseCode = "400", description = "Datos inválidos: cliente o carrito no encontrados")
    })
    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVenta(@RequestBody RegistrarVentaDTO dto) {
        var cliente = clienteRepository.findById(dto.getId_cliente()).orElse(null);
        var carrito = carritoRepository.findById(dto.getId_carrito()).orElse(null);

        if (cliente == null || carrito == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos: cliente o carrito no encontrados");
        }

        Long total = carrito.getItems().stream()
                .mapToLong(item -> item.getProducto().getPrecio_producto() * item.getCantidad())
                .sum();

        Venta venta = new Venta();
        venta.setCliente(cliente);
        venta.setCarrito(carrito);
        venta.setFecha_venta(LocalDateTime.now());
        venta.setTotal_venta(total);

        Venta ventaGuardada = ventaService.save(venta);
        carritoRepository.save(carrito); // si necesitas actualizar carrito

        return ResponseEntity
                .created(linkTo(methodOn(VentaControllerV2.class).buscar(ventaGuardada.getId_venta())).toUri())
                .body(ventaAssembler.toModel(ventaGuardada));
    }

    @Operation(summary = "Buscar venta por ID", description = "Obtiene una venta específica mediante su ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Venta encontrada"),
            @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("/{id}/buscar")
    public ResponseEntity<EntityModel<Venta>> buscar(@PathVariable Integer id) {
        try {
            Venta venta = ventaService.findById(id);
            return ResponseEntity.ok(ventaAssembler.toModel(venta));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

}
