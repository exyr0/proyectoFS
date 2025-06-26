package com.perfulandia_spa.cl.Perfulandia_SPA.controller;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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

    @Operation(summary = "Listar todos los carritos", description = "Obtiene la lista de todos los carritos en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de carritos encontrada"),
        @ApiResponse(responseCode = "204", description = "No se encontraron carritos")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Carrito>> listar(){
        List<Carrito> carritos = carritoService.findAll();
        if(carritos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(carritos);
    }
    @Operation(summary = "Buscar carrito por ID", description = "Obtiene un carrito específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @GetMapping("/{id}/buscar")
    public ResponseEntity<Carrito> buscar(@PathVariable Integer id){
        try{
            Carrito carrito = carritoService.findById(id);
            return ResponseEntity.ok(carrito);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
    
    @Operation(summary = "Actualizar carrito", description = "Actualiza un carrito especifico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @PutMapping("/{id}/actualizar")
public ResponseEntity<?> actualizarCarrito(@PathVariable Integer id, @RequestBody Carrito carritoActualizado) {
    try {
        Carrito carritoExistente = carritoService.findById(id);
        carritoExistente.setCliente(carritoActualizado.getCliente());

        // Limpiamos y reemplazamos los ítems con los nuevos
        carritoExistente.getItems().clear();
        carritoExistente.getItems().addAll(carritoActualizado.getItems());

        carritoService.save(carritoExistente);
        return ResponseEntity.ok(carritoExistente);
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado");
        }
    }
    @Operation(summary = "Eliminar Carrito", description = "Elimina un carrito.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminarCarrito(@PathVariable Long id) {
    try {
        carritoService.deleteById(id);
        return ResponseEntity.noContent().build();
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Carrito no encontrado");
    }
    }
     @Operation(summary = "Agregar un producto al carrito", description = "Agrega un producto al carrito especificado con la cantidad indicada")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto agregado al carrito"),
        @ApiResponse(responseCode = "404", description = "Carrito o Producto no encontrado")
    })

    @PostMapping("{carritoId}/agregar")
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
    @Operation(summary = "Crear un nuevo carrito", description = "Crea un nuevo carrito para un cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Carrito creado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })

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


    
    
    

