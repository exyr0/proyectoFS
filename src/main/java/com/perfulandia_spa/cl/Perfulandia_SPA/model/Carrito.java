package com.perfulandia_spa.cl.Perfulandia_SPA.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Entity
@Table(name= "Carrito")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Carrito {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id_carrito;

    @OneToOne
    @JoinColumn(name = "cliente_id", unique = true)
    @JsonIgnore
    private Cliente cliente;

    @OneToMany(cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemCarrito> items = new ArrayList<>();

    public void agregarItem(Producto producto, int cantidad) {
        for (ItemCarrito item : items) {
            if (item.getProducto().getId_producto().equals(producto.getId_producto())){
                item.setCantidad(item.getCantidad() + cantidad);
                return;
            }
        }
        ItemCarrito nuevoItem = new ItemCarrito();
        nuevoItem.setProducto(producto);
        nuevoItem.setCantidad(cantidad);
        items.add(nuevoItem);
    }
}

