package com.perfulandia_spa.cl.Perfulandia_SPA.assemblers;
import com.perfulandia_spa.cl.Perfulandia_SPA.controller.ClienteControllerV2;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Cliente;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;


@Component
public class ClienteModelAssembler implements RepresentationModelAssembler<Cliente, EntityModel<Cliente>> {

    @Override
    public EntityModel<Cliente> toModel(Cliente cliente) {
        return EntityModel.of(cliente,
                // Self link
                linkTo(methodOn(ClienteControllerV2.class).buscar(cliente.getId_cliente())).withSelfRel(),
                // Link to list all clientes
                linkTo(methodOn(ClienteControllerV2.class).listar()).withRel("clientes"),
                // Link to update this cliente
                linkTo(methodOn(ClienteControllerV2.class).actualizar(cliente.getId_cliente(), cliente)).withRel("actualizar"),
                // Link to delete this cliente
                linkTo(methodOn(ClienteControllerV2.class).eliminar(cliente.getId_cliente().longValue())).withRel("eliminar")
        );
    }
}