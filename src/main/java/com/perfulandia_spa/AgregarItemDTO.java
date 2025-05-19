package com.perfulandia_spa;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Data
public class AgregarItemDTO {
    private Long productoId;
    private Integer cantidad;
}
