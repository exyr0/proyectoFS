package com.perfulandia_spa.cl.Perfulandia_SPA.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Table(name= "Item")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCarrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonIgnore 
    private Integer Nro_item;

    @ManyToOne
    private Producto producto;
    private int cantidad;
}
