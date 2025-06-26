package com.perfulandia_spa.cl.Perfulandia_SPA;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Producto;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ProductoRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.ProductoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class ProductoServiceTest {

    @Mock
    private ProductoRepository productoRepository;

    @InjectMocks
    private ProductoService productoService;

    private Producto producto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        producto = new Producto();
        producto.setId_producto(1);
        producto.setNombre_producto("Jabón Natural");
        producto.setPrecio_producto(3500);
        producto.setStock(20);
    }

    @Test
    void testFindAll() {
        when(productoRepository.findAll()).thenReturn(List.of(producto));

        List<Producto> productos = productoService.findAll();

        assertEquals(1, productos.size());
        verify(productoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(productoRepository.findById(1L)).thenReturn(Optional.of(producto));

        Producto resultado = productoService.findById(1L);

        assertNotNull(resultado);
        assertEquals("Jabón Natural", resultado.getNombre_producto());
        verify(productoRepository).findById(1L);
    }

    @Test
    void testSave() {
        when(productoRepository.save(producto)).thenReturn(producto);

        Producto resultado = productoService.save(producto);

        assertEquals(3500, resultado.getPrecio_producto());
        verify(productoRepository).save(producto);
    }

    @Test
    void testDelete() {
        doNothing().when(productoRepository).deleteById(1L);

        productoService.delete(1L);

        verify(productoRepository, times(1)).deleteById(1L);
    }
}
