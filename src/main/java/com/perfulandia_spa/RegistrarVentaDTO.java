package com.perfulandia_spa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data

public class RegistrarVentaDTO {
    private Long id_cliente;
    private Long id_carrito;
}