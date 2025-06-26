package com.perfulandia_spa.cl.Perfulandia_SPA.controller;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Producto;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.ProductoService;

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
@RequestMapping("/api/v1/productos")
public class ProductoController {
    @Autowired
    private ProductoService productoService;

    @Operation(summary = "Listar todos los productos", description = "Obtiene una lista con todos los productos registrados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Lista de productos obtenida"),
        @ApiResponse(responseCode = "204", description = "No hay productos registrados")
    })

    @GetMapping("/listar")
    public ResponseEntity<List<Producto>> listar(){
        List<Producto> productos = productoService.findAll();
        if(productos.isEmpty()){
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(productos);
    }

    @Operation(summary = "Crear un nuevo producto", description = "Crea un producto nuevo con los datos proporcionados")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "201", description = "Producto creado exitosamente")
    })

    @PostMapping("/guardar")
    public ResponseEntity<Producto> guardar(@RequestBody Producto producto){
        Producto productoNuevo = productoService.save(producto);
        return ResponseEntity.status(HttpStatus.CREATED).body(productoNuevo);
    }

    @Operation(summary = "Buscar producto por ID", description = "Obtiene un producto específico mediante su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto encontrado"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })

    @GetMapping("/buscar")
    public ResponseEntity<Producto> buscar(@PathVariable Integer id){
        try{
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(producto);
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Actualizar producto", description = "Actualiza un producto existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Producto actualizado exitosamente"),
        @ApiResponse(responseCode = "204", description = "No se encontró producto para actualizar")
    })

    @PutMapping("/actualizar")
    public ResponseEntity<Producto> actualizar(@PathVariable Integer id, @RequestBody Producto producto){
        try{
            Producto pro = productoService.findById(id);
            pro.setNombre_producto(producto.getNombre_producto());
            pro.setId_producto(id);
            pro.setPrecio_producto(producto.getPrecio_producto());
            productoService.save(pro);
            return ResponseEntity.ok(pro);
        }catch(Exception e){
            return ResponseEntity.noContent().build();
        }
    }

    @Operation(summary = "Eliminar producto", description = "Elimina un producto por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Producto eliminado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Producto no encontrado")
    })

    @DeleteMapping("/eliminar")
    public ResponseEntity<Producto> eliminar(@PathVariable Long id){
        try{
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        }catch(Exception e){
            return ResponseEntity.notFound().build();
        }
    }
}
