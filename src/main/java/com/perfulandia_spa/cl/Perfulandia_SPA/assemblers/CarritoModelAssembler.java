package com.perfulandia_spa.cl.Perfulandia_SPA.assemblers;

import com.perfulandia_spa.cl.Perfulandia_SPA.controller.CarritoControllerV2;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Carrito;

import io.micrometer.common.lang.NonNull;

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
                linkTo(methodOn(CarritoControllerV2.class).buscar(carrito.getId_carrito().intValue())).withSelfRel(),

                // Link a listar todos los carritos
                linkTo(methodOn(CarritoControllerV2.class).listar()).withRel("carritos"),

                // Link a agregar un item
                linkTo(methodOn(CarritoControllerV2.class).agregarItemAlCarrito(carrito.getId_carrito().longValue(), null)).withRel("agregar-item"),

                // Link a eliminar carrito
                linkTo(methodOn(CarritoControllerV2.class).eliminarCarrito(carrito.getId_carrito().longValue())).withRel("eliminar")
        );
    }
}