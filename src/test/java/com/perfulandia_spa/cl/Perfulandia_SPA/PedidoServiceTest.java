package com.perfulandia_spa.cl.Perfulandia_SPA;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Pedido;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.PedidoRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.PedidoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class PedidoServiceTest {

    @Mock
    private PedidoRepository pedidoRepository;

    @InjectMocks
    private PedidoService pedidoService;

    private Pedido pedido;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        pedido = new Pedido();
        pedido.setId_pedido(1);
        pedido.setEstado_reserva("pendiente");
        pedido.setFecha_pedido(new java.sql.Date(System.currentTimeMillis()));
    }

    @Test
    void testListarTodos() {
        when(pedidoRepository.findAll()).thenReturn(List.of(pedido));

        List<Pedido> lista = pedidoService.findAll();

        assertEquals(1, lista.size());
        verify(pedidoRepository, times(1)).findAll();
    }

    @Test
    void testBuscarPorId() {
        when(pedidoRepository.findById(1L)).thenReturn(Optional.of(pedido));

        Pedido encontrado = pedidoService.findById(1L);

        assertNotNull(encontrado);  // verificamos que no sea null
        assertEquals("pendiente", encontrado.getEstado_reserva());  // comparamos directamente el valor
        verify(pedidoRepository).findById(1L);
    }

    @Test
    void testGuardar() {
        when(pedidoRepository.save(pedido)).thenReturn(pedido);

        Pedido guardado = pedidoService.save(pedido);

        assertNotNull(guardado);
        assertEquals("pendiente", guardado.getEstado_reserva());
        verify(pedidoRepository).save(pedido);
    }

    @Test
    void testEliminar() {
        doNothing().when(pedidoRepository).deleteById(1L);

        pedidoService.delete(1L);

        verify(pedidoRepository, times(1)).deleteById(1L);
    }
}
