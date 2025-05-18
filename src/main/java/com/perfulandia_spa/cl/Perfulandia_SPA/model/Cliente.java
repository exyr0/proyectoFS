package com.perfulandia_spa.cl.Perfulandia_SPA.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name= "Cliente")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_cliente;

    @OneToOne(mappedBy = "cliente", cascade = CascadeType.ALL, orphanRemoval = true)
    private Carrito carrito;

    @Column(unique=true, nullable=false)
    private String usuario;

    @Column(unique=true, nullable=false)
    private String contraseña;

    @Column(unique=true, length = 50, nullable=false)
    private String nombre;

    @Column(unique=true, length = 30, nullable=false)
    private String correo;
}
