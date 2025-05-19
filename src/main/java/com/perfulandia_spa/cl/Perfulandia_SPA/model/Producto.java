package com.perfulandia_spa.cl.Perfulandia_SPA.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name= "Producto")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Producto {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_producto;

    @Column(unique=true, length = 50, nullable=false)
    private String nombre_producto;

    @Column(unique=false, nullable=false)
    private Integer stock;
    
    @Column(unique=false, nullable = false)
    private Integer precio_producto;



}
