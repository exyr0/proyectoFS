package com.perfulandia_spa.cl.Perfulandia_SPA.controller;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Carrito;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Cliente;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Venta;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.VentaService;
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
    @GetMapping()
    public ResponseEntity<List<Venta>> listar(){
        List<Venta> ventas = ventaService.findAll();
        if(ventas.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ventas);
    }
    @PostMapping
    public ResponseEntity<Venta> guardar(@RequestBody Venta venta){
        Venta ventaNueva = ventaService.save(venta);
        return ResponseEntity.status(HttpStatus.CREATED).body(ventaNueva);
    }
    @PostMapping("/venta")
    public ResponseEntity<?> registrarVenta(@RequestBody RegistrarVentaDTO dto) {
        Cliente cliente = clienteRepository.findById(dto.getId_cliente()).orElse(null);
        Carrito carrito = carritoRepository.findById(dto.getId_carrito()).orElse(null);

        if (cliente == null || carrito == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Datos inválidos");
        }
    double total = carrito.getItems().stream()
        .mapToDouble(item -> item.getProducto().getPrecio_producto() * item.getCantidad())
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
    @GetMapping("/{id}")
    public ResponseEntity<Venta> buscar(@PathVariable Integer id){
        try{
            Venta venta = ventaService.findById(id);
            return ResponseEntity.ok(venta);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }



}
