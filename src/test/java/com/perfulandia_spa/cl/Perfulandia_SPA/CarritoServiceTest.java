package com.perfulandia_spa.cl.Perfulandia_SPA;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Carrito;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.CarritoRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.CarritoService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

class CarritoServiceTest {

    @Mock
    private CarritoRepository carritoRepository;

    @InjectMocks
    private CarritoService carritoService;

    private Carrito carrito;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        carrito = new Carrito();
        carrito.setId_carrito(1);
        // puedes agregar más campos si existen
    }

    @Test
    void testFindAll() {
        List<Carrito> carritos = List.of(carrito);
        when(carritoRepository.findAll()).thenReturn(carritos);

        List<Carrito> result = carritoService.findAll();

        assertEquals(1, result.size());
        verify(carritoRepository, times(1)).findAll();
    }

    @Test
    void testFindById() {
        when(carritoRepository.findById(1L)).thenReturn(Optional.of(carrito));

        Carrito found = carritoService.findById(1L);

        assertNotNull(found);
        assertEquals(1, found.getId_carrito());
        verify(carritoRepository).findById(1L);
    }

    @Test
    void testSave() {
        when(carritoRepository.save(carrito)).thenReturn(carrito);

        Carrito saved = carritoService.save(carrito);

        assertNotNull(saved);
        verify(carritoRepository).save(carrito);
    }

    @Test
    void testDeleteById() {
        doNothing().when(carritoRepository).deleteById(1L);

        carritoService.deleteById(1L);

        verify(carritoRepository, times(1)).deleteById(1L);
    }
}