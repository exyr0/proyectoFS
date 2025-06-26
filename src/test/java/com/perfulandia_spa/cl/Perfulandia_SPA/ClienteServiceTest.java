package com.perfulandia_spa.cl.Perfulandia_SPA;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.*;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Cliente;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ClienteRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.ClienteService;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        cliente = new Cliente();
        cliente.setId_cliente(1);
        cliente.setNombre("Kevin");
        cliente.setCorreo("kevin@email.com");
        cliente.setUsuario("kevin123");
        cliente.setContraseña("123456"); 
    }

    @Test
    void testFindAll() {
        List<Cliente> clientes = List.of(cliente);
        when(clienteRepository.findAll()).thenReturn(clientes);

        List<Cliente> result = clienteService.findAll();

        assertEquals(1, result.size());
        assertEquals("Kevin", result.get(0).getNombre());
    }

    @Test
    void testFindById() {
        when(clienteRepository.findById(1L)).thenReturn(Optional.of(cliente));

        Cliente found = clienteService.findById(1L);

        assertNotNull(found);
        assertEquals("Kevin", found.getNombre());
    }

    @Test
    void testSaveEncryptsPassword() {
        when(clienteRepository.save(any(Cliente.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Cliente saved = clienteService.save(cliente);

        assertNotNull(saved);
        assertNotEquals("123456", saved.getContraseña());
        assertTrue(new BCryptPasswordEncoder().matches("123456", saved.getContraseña()));
        verify(clienteRepository).save(any(Cliente.class));
    }

    @Test
    void testDelete() {
        doNothing().when(clienteRepository).deleteById(1L);

        clienteService.delete(1L);

        verify(clienteRepository, times(1)).deleteById(1L);
    }
}
