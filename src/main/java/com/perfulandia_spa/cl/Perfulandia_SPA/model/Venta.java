package com.perfulandia_spa.cl.Perfulandia_SPA.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
<<<<<<< HEAD
import java.sql.Date;
=======
import java.time.LocalDateTime;

>>>>>>> origin/feature-avance-kevin


@Entity
@Table(name= "Venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_venta;

<<<<<<< HEAD
    @Column(unique=true, length = 50, nullable=false)
    private String medio_pago;

    @Column(unique=true, length = 50, nullable=false)
    private Date fecha_venta;

    @Column(unique=true, length = 50, nullable=false)
    private Integer total_venta;

    @Column(unique=true, length = 50, nullable=false)
    private String estado_venta;
=======
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

>>>>>>> origin/feature-avance-kevin
}
