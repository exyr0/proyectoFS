package com.perfulandia_spa.cl.Perfulandia_SPA.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;



@Entity
@Table(name= "Venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_venta;

    @ManyToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;

    @OneToOne
    @JoinColumn(name = "carrito_id")
    private Carrito carrito;

    private String medio_pago;

    private LocalDateTime fecha_venta;
    private Double total_venta;
    private String estado_venta;

}
