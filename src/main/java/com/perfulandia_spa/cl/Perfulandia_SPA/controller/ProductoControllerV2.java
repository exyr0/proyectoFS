package com.perfulandia_spa.cl.Perfulandia_SPA.controller;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Producto;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.ProductoService;
import com.perfulandia_spa.cl.Perfulandia_SPA.assemblers.ProductoModelAssembler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
@RequestMapping("/api/v2/productos")
public class ProductoControllerV2 {

    @Autowired
    private ProductoService productoService;

    @Autowired
    private ProductoModelAssembler productoAssembler;

    @GetMapping("/listar")
    public ResponseEntity<CollectionModel<EntityModel<Producto>>> listar() {
        List<Producto> productos = productoService.findAll();

        if (productos.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        List<EntityModel<Producto>> productosModel = productos.stream()
                .map(productoAssembler::toModel)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                CollectionModel.of(productosModel,
                        linkTo(methodOn(ProductoControllerV2.class).listar()).withSelfRel()));
    }

    @PostMapping("/guardar")
    public ResponseEntity<EntityModel<Producto>> guardar(@RequestBody Producto producto) {
        Producto nuevo = productoService.save(producto);
        return ResponseEntity
                .created(linkTo(methodOn(ProductoControllerV2.class).buscar(nuevo.getId_producto())).toUri())
                .body(productoAssembler.toModel(nuevo));
    }

    @GetMapping("/{id}/buscar")
    public ResponseEntity<EntityModel<Producto>> buscar(@PathVariable Integer id) {
        try {
            Producto producto = productoService.findById(id);
            return ResponseEntity.ok(productoAssembler.toModel(producto));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    @PutMapping("/{id}/actualizar")
    public ResponseEntity<EntityModel<Producto>> actualizar(@PathVariable Integer id, @RequestBody Producto producto) {
        try {
            Producto pro = productoService.findById(id);
            pro.setNombre_producto(producto.getNombre_producto());
            pro.setPrecio_producto(producto.getPrecio_producto());
            productoService.save(pro);
            return ResponseEntity.ok(productoAssembler.toModel(pro));
        } catch (Exception e) {
            return ResponseEntity.noContent().build();
        }
    }

    @DeleteMapping("/{id}/eliminar")
    public ResponseEntity<Void> eliminar(@PathVariable Long id) {
        try {
            productoService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }
}
