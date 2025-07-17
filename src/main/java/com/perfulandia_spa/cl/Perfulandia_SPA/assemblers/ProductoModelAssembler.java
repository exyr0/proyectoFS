package com.perfulandia_spa.cl.Perfulandia_SPA.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandia_spa.cl.Perfulandia_SPA.controller.ProductoController;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Producto;
import org.springframework.lang.NonNull;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

@Component
public class ProductoModelAssembler implements RepresentationModelAssembler<Producto, EntityModel<Producto>> {
    @Override
    @NonNull
    public EntityModel<Producto> toModel(Producto producto) {
        return EntityModel.of(producto,
            // Link a sí mismo (buscar producto por id)
            linkTo(methodOn(ProductoController.class).buscar(producto.getId_producto())).withSelfRel(),

            // Link para listar todos los productos
            linkTo(methodOn(ProductoController.class).listar()).withRel("productos"),

            // Link para crear un producto (generalmente POST, aquí solo referencia)
            linkTo(methodOn(ProductoController.class).guardar(null)).withRel("crear"),

            // Link para actualizar producto
            linkTo(methodOn(ProductoController.class).actualizar(producto.getId_producto(), null)).withRel("actualizar"),

            // Link para eliminar producto
            linkTo(methodOn(ProductoController.class).eliminar(producto.getId_producto().longValue())).withRel("eliminar")
        );
    }
}
