package com.perfulandia_spa.cl.Perfulandia_SPA;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.*;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Venta;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.VentaRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.VentaService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class VentaServiceTest {

    @Mock
    private VentaRepository ventaRepository;

    @InjectMocks
    private VentaService ventaService;

    private Venta venta;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        venta = new Venta();
        venta.setId_venta(1);
        venta.setFecha_venta(LocalDateTime.now());
        venta.setMedio_pago("Efectivo");
        venta.setEstado_venta("Completado");
        venta.setTotal_venta(29990.0);
        venta.setCarrito(null); // Ignoramos relaciones
        venta.setCliente(null);
    }

    @Test
    void testFindAll() {
        when(ventaRepository.findAll()).thenReturn(List.of(venta));

        List<Venta> ventas = ventaService.findAll();

        assertEquals(1, ventas.size());
        verify(ventaRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(ventaRepository.findById(1L)).thenReturn(Optional.of(venta));

        Venta resultado = ventaService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Efectivo", resultado.getMedio_pago());
        verify(ventaRepository).findById(1L);
    }

    @Test
    void testSave() {
        when(ventaRepository.save(venta)).thenReturn(venta);

        Venta resultado = ventaService.save(venta);

        assertNotNull(resultado);
        assertEquals("Completado", resultado.getEstado_venta());
        verify(ventaRepository).save(venta);
    }

    @Test
    void testDelete() {
        doNothing().when(ventaRepository).deleteById(1L);

        ventaService.delete(1L);

        verify(ventaRepository).deleteById(1L);
    }
}