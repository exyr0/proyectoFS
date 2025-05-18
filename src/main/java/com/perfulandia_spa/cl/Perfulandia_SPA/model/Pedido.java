package com.perfulandia_spa.cl.Perfulandia_SPA.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;


@Entity
@Table(name= "Pedido")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_pedido;

    @Column(unique=false, length = 50, nullable=false)
    private Date fecha_pedido;

    @Column(unique=false,length = 10, nullable=false)
    private String estado_reserva;
}
