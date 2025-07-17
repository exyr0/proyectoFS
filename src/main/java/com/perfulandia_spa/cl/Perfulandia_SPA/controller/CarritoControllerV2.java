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
import com.perfulandia_spa.cl.Perfulandia_SPA.assemblers.CarritoModelAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

import java.util.List;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;

@RestController
@RequestMapping("api/v2/carrito")
public class CarritoControllerV2 {

    @Autowired
    private CarritoService carritoService;

    @Autowired
    private CarritoRepository carritoRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private CarritoModelAssembler carritoAssembler;

    @Operation(summary = "Listar todos los carritos", description = "Obtiene la lista de todos los carritos en el sistema")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de carritos encontrada"),
        @ApiResponse(responseCode = "204", description = "No se encontraron carritos")
    })
    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Carrito>>> listar() {
        List<Carrito> carritos = carritoService.findAll();
        if (carritos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        List<EntityModel<Carrito>> carritoModels = carritos.stream()
            .map(carritoAssembler::toModel)
            .toList();

        return ResponseEntity.ok(
            CollectionModel.of(carritoModels,
                linkTo(methodOn(CarritoControllerV2.class).listar()).withSelfRel()
            )
        );
    }

    @Operation(summary = "Buscar carrito por ID", description = "Obtiene un carrito específico por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito encontrado"),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @GetMapping("/{id}/buscar")
    public ResponseEntity<EntityModel<Carrito>> buscar(@PathVariable Integer id) {
        try {
            Carrito carrito = carritoService.findById(id);
            return ResponseEntity.ok(carritoAssembler.toModel(carrito));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar carrito", description = "Actualiza un carrito específico por su ID.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Carrito actualizado"),
        @ApiResponse(responseCode = "404", description = "Carrito no encontrado")
    })
    @PutMapping("/{id}/actualizar")
    public ResponseEntity<EntityModel<Carrito>> actualizarCarrito(
            @PathVariable Integer id,
            @RequestBody Carrito carritoActualizado) {
        try {
            Carrito carritoExistente = carritoService.findById(id);
            carritoExistente.setCliente(carritoActualizado.getCliente());
            carritoExistente.getItems().clear();
            carritoExistente.getItems().addAll(carritoActualizado.getItems());
            carritoService.save(carritoExistente);
            return ResponseEntity.ok(carritoAssembler.toModel(carritoExistente));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @Operation(summary = "Eliminar carrito", description = "Elimina un carrito.")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Carrito eliminado"),
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
    public ResponseEntity<EntityModel<Carrito>> agregarItemAlCarrito(
            @PathVariable Long carritoId,
            @RequestBody AgregarItemDTO dto) {

        Carrito carrito = carritoRepository.findById(carritoId).orElse(null);
        Producto producto = productoRepository.findById(dto.getProductoId()).orElse(null);

        if (carrito == null || producto == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        carrito.agregarItem(producto, dto.getCantidad());
        producto.setStock(producto.getStock() - dto.getCantidad());
        carritoRepository.save(carrito);
        return ResponseEntity.ok(carritoAssembler.toModel(carrito));
    }

    @Operation(summary = "Crear un nuevo carrito", description = "Crea un nuevo carrito para un cliente existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Carrito creado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
    })
    @PostMapping("/crear")
    public ResponseEntity<EntityModel<Carrito>> crearCarrito(@RequestBody DtoAgregarCliente dto) {
        Cliente cliente = clienteRepository.findById(dto.getId_cliente()).orElse(null);

        if (cliente == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        Carrito carrito = new Carrito();
        carrito.setCliente(cliente);
        carritoRepository.save(carrito);

        return ResponseEntity.status(HttpStatus.CREATED).body(carritoAssembler.toModel(carrito));
    }
}
