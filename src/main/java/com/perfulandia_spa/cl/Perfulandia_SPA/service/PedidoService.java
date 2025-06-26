package com.perfulandia_spa.cl.Perfulandia_SPA.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Pedido;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.PedidoRepository;

@Service
public class PedidoService {

    private final PedidoRepository pedidoRepository;

    public PedidoService(PedidoRepository pedidoRepository) {
        this.pedidoRepository = pedidoRepository;
    }

    public List<Pedido> findAll() {
        return pedidoRepository.findAll();
    }

    public Pedido findById(long id) {
        return pedidoRepository.findById(id).get();
    }

    public Pedido save(Pedido pedido) {
        return pedidoRepository.save(pedido);
    }

    public void delete(long id) {
        pedidoRepository.deleteById(id);
    }
}
