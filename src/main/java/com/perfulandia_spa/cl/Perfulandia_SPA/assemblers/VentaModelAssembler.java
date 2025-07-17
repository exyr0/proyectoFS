package com.perfulandia_spa.cl.Perfulandia_SPA.assemblers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import com.perfulandia_spa.cl.Perfulandia_SPA.controller.VentaControllerV2;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Venta;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import org.springframework.lang.NonNull;

@Component
public class VentaModelAssembler implements RepresentationModelAssembler<Venta, EntityModel<Venta>> {
    
    @Override
    @NonNull
    public EntityModel<Venta> toModel(Venta venta) {
        return EntityModel.of(venta,
            linkTo(methodOn(VentaControllerV2.class).buscar(venta.getId_venta())).withSelfRel(),
            linkTo(methodOn(VentaControllerV2.class).listar()).withRel("ventas"),
            linkTo(methodOn(VentaControllerV2.class).registrarVenta(null)).withRel("registrar")
        );
    }
}
