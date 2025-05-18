package com.perfulandia_spa.cl.Perfulandia_SPA.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.sql.Date;


@Entity
@Table(name= "Venta")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Venta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_venta;

    @Column(unique=true, length = 50, nullable=false)
    private String medio_pago;

    @Column(unique=true, length = 50, nullable=false)
    private Date fecha_venta;

    @Column(unique=true, length = 50, nullable=false)
    private Integer total_venta;

    @Column(unique=true, length = 50, nullable=false)
    private String estado_venta;
}
