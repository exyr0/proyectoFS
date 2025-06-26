package com.perfulandia_spa.cl.Perfulandia_SPA;

import net.datafaker.Faker;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.perfulandia_spa.cl.Perfulandia_SPA.model.Cliente;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Pedido;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Producto;
import com.perfulandia_spa.cl.Perfulandia_SPA.model.Venta;
import com.perfulandia_spa.cl.Perfulandia_SPA.service.ClienteService;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ClienteRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.PedidoRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.ProductoRepository;
import com.perfulandia_spa.cl.Perfulandia_SPA.repository.VentaRepository;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.List;
@Component
public class DataLoader implements CommandLineRunner {

    @Autowired
    private ClienteRepository clienteRepository;

    @Autowired
    private ProductoRepository productoRepository;

    @Autowired
    private PedidoRepository pedidoRepository;

    @Autowired
    private VentaRepository ventaRepository;

    @Autowired
    private ClienteService clienteService;

    public DataLoader(ClienteRepository clienteRepository, ProductoRepository productoRepository, PedidoRepository pedidoRepository, VentaRepository ventaRepository) {
        this.pedidoRepository = pedidoRepository;
        this.clienteRepository = clienteRepository;
        this.productoRepository = productoRepository;
        this.ventaRepository = ventaRepository;
    }

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        Faker faker = new Faker(Locale.of("es","CL"));
        Random random = new Random();

        //Crear Clientes
        for (int i = 0; i < 30; i++) {
            Cliente cliente = new Cliente();
            cliente.setUsuario(faker.internet().username() + i);
            cliente.setContraseña(faker.internet().password(8, 16));
            cliente.setNombre(faker.name().fullName());
            cliente.setCorreo(faker.internet().emailAddress());
            clienteRepository.save(cliente);
        }

        //Crear Productos
        for (int i = 0; i < 50; i++) {
            String baseNombre = faker.commerce().productName();
            String nombreUnico = baseNombre + " #" + i;

            Producto producto = new Producto();
            producto.setNombre_producto(nombreUnico);
            producto.setPrecio_producto(faker.number().numberBetween(1000, 100000));
            producto.setStock(faker.number().numberBetween(1, 100));
            productoRepository.save(producto);
        }

        //Creacion de estados de pedido
        String[] estados = {"pendiente", "confirmado", "cancelado"};

        //Creacion de Pedidos
        for (int i = 0; i < 30; i++) {
            Pedido pedido = new Pedido();

            
            Date fecha = faker.date().past(365, TimeUnit.DAYS);
            pedido.setFecha_pedido(fecha);

            
            String estado = estados[faker.number().numberBetween(0, estados.length)];
            pedido.setEstado_reserva(estado);
            pedidoRepository.save(pedido);
        }

        //Creacion de medios de pago y estado de venta
        String[] mediosPago = {"Efectivo", "Tarjeta de crédito", "Transferencia", "MercadoPago"};
        String[] estadosVenta = {"Pendiente", "Completada", "Cancelada"};


        //Creacion de ventas
        for (int i = 0; i < 30; i++) {
            Venta venta = new Venta();
            List<Cliente> clientes = clienteService.findAll();          
            venta.setCliente(clientes.get(random.nextInt(clientes.size())));            
            venta.setMedio_pago(mediosPago[random.nextInt(mediosPago.length)]);
            venta.setEstado_venta(estadosVenta[random.nextInt(estadosVenta.length)]);
            venta.setFecha_venta(LocalDateTime.now().minusDays(random.nextInt(365)).truncatedTo(ChronoUnit.SECONDS));
            venta.setTotal_venta(Math.round(1000 + (100000 - 1000) * random.nextDouble()));
            ventaRepository.save(venta);
        }
    }
}
