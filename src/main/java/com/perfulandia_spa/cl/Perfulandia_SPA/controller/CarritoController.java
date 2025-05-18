package com.perfulandia_spa.cl.Perfulandia_SPA.controller;

import com.perfulandia_spa.AgregarItemDTO;
import com.perfulandia_spa.DtoAgregarCliente;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Carrito;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Cliente;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Producto;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.CarritoRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ClienteRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ProductoRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



import java.util.List;



@RestController
@RequestMapping("api/v1/carrito")
public class CarritoController {
    @Autowired
    private CarritoService carritoService;
    
    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @GetMapping()
    public ResponseEntity<List<Carrito>> listar(){
        List<Carrito> carritos = carritoService.findAll();
        if(carritos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carritos);
    }
    @PostMapping()
    public ResponseEntity<Carrito> guardar(@RequestBody Carrito carrito){
        Carrito carritoNuevo = carritoService.save(carrito);
        return ResponseEntity.status(HttpStatus.CREATED).body(carritoNuevo);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Carrito> buscar(@PathVariable Integer id){
        try{
            Carrito carrito = carritoService.findById(id);
            return ResponseEntity.ok(carrito);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/{carritoId}/agregar")
    public ResponseEntity<?> agregarItemAlCarrito(
            @PathVariable Long carritoId,
            @RequestBody AgregarItemDTO dto) {

        Carrito carrito = carritoRepository.findById(carritoId).orElse(null);
        Producto producto = productoRepository.findById(dto.getProductoId()).orElse(null);

        if (carrito == null || producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito o Producto no encontrado.");
        }

        carrito.agregarItem(producto, dto.getCantidad());
        producto.setStock(producto.getStock() - dto.getCantidad());
        carritoRepository.save(carrito);
        return ResponseEntity.ok(carrito);
    }

    @PostMapping("/crear")
    public ResponseEntity<?> crearCarrito(@RequestBody DtoAgregarCliente dto) {
    Cliente cliente = clienteRepository.findById(dto.getId_cliente()).orElse(null);

    if (cliente == null) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Cliente no encontrado.");
    }

    Carrito carrito = new Carrito();
    carrito.setCliente(cliente);
    carritoRepository.save(carrito);

    return ResponseEntity.status(HttpStatus.CREATED).body(carrito);
}

    
}


    
    
    

