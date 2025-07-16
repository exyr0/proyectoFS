package com.perfulandia_spa.cl.Perfulandia_SPA.assembler;

import com.perfulandia_spa.cl.Perfulandia_SPA.controller.CarritoController;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Carrito;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class CarritoModelAssembler implements RepresentationModelAssembler<Carrito, EntityModel<Carrito>> {

    @Override
    public EntityModel<Carrito> toModel(Carrito carrito) {
        return EntityModel.of(carrito,
                // Self link
                linkTo(methodOn(CarritoController.class).buscar(carrito.getId().intValue())).withSelfRel(),

                // Link a listar todos los carritos
                linkTo(methodOn(CarritoController.class).listar()).withRel("carritos"),

                // Link a agregar un item
                linkTo(methodOn(CarritoController.class).agregarItemAlCarrito(carrito.getId(), null)).withRel("agregar-item"),

                // Link a eliminar carrito
                linkTo(methodOn(CarritoController.class).eliminarCarrito(carrito.getId())).withRel("eliminar")
        );
    }
}