package com.perfulandia_spa.cl.Perfulandia_SPA.assemblers;

import com.perfulandia_spa.cl.Perfulandia_SPA.controller.PedidoControllerV2;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Pedido;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.lang.NonNull;

@Component
public class PedidoModelAssembler implements RepresentationModelAssembler<Pedido, EntityModel<Pedido>> {

    @Override
    @NonNull
    public EntityModel<Pedido> toModel(Pedido pedido) {
        return EntityModel.of(pedido,
            linkTo(methodOn(PedidoControllerV2.class).buscar(pedido.getId_pedido().longValue())).withSelfRel(),
            linkTo(methodOn(PedidoControllerV2.class).listar()).withRel("pedidos"),
            linkTo(methodOn(PedidoControllerV2.class).eliminar(pedido.getId_pedido().longValue())).withRel("eliminar")
        );
    }
}