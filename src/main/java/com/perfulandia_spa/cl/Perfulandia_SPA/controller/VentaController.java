package com.perfulandia_spa.cl.Perfulandia_SPA.controller;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Carrito;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Cliente;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Venta;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.VentaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

import com.perfulandia_spa.RegistrarVentaDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.time.LocalDateTime;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ClienteRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.CarritoRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.VentaRepository;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ventas")
public class VentaController {
    @Autowired
    private VentaService ventaService;
    @Autowired
    private VentaRepository ventaRepository;
    @Autowired
    private ClienteRepository clienteRepository;
    @Autowired
    private CarritoRepository carritoRepository;

    @Operation(summary = "Listar todas las ventas", description = "Obtiene una lista con todas las ventas registradas")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de ventas encontrada"),
        @ApiResponse(responseCode = "204", description = "No existen ventas registradas")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> ventas = ventaService.findAll();
        if(ventas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }
    @Operation(summary = "Guardar una venta", description = "Crea una nueva venta con la información provista")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Venta creada exitosamente")
    })

    @PostMapping("/guardar")
    public ResponseEntity<Venta> guardar(@RequestBody Venta venta){
        Venta ventaNueva = ventaService.save(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaNueva);
    }
    @Operation(summary = "Registrar una venta desde carrito y cliente", description = "Registra una venta basada en un carrito y cliente existentes")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta registrada exitosamente"),
        @ApiResponse(responseCode = "400", description = "Datos inválidos: cliente o carrito no encontrados")
    })

    @PostMapping("/registrar")
    public ResponseEntity<?> registrarVenta(@RequestBody RegistrarVentaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getId_cliente()).orElse(null);
        Carrito carrito = carritoRepository.findById(dto.getId_carrito()).orElse(null);

        if (cliente == null || carrito == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos");
        }
    Long total = carrito.getItems().stream()
        .mapToLong(item -> item.getProducto().getPrecio_producto() * item.getCantidad())
        .sum();

    Venta venta = new Venta();
    venta.setCliente(cliente);
    venta.setCarrito(carrito);
    venta.setFecha_venta(LocalDateTime.now());
    venta.setTotal_venta(total);

    ventaRepository.save(venta);
    carritoRepository.save(carrito);

    return ResponseEntity.ok(venta);
}
    @Operation(summary = "Buscar venta por ID", description = "Obtiene una venta específica mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Venta encontrada"),
        @ApiResponse(responseCode = "404", description = "Venta no encontrada")
    })
    @GetMapping("{id}/buscar")
    public ResponseEntity<Venta> buscar(@PathVariable Integer id){
        try{
            Venta venta = ventaService.findById(id);
            return ResponseEntity.ok(venta);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }



}
