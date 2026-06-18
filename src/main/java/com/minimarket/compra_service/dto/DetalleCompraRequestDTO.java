package com.minimarket.compra_service.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompraRequestDTO {

    @NotNull(message = "EL ID DEL PRODUCTO ES OBLIGATORIO")
    @Positive(message = "EL ID DEL PRODUCTO DEBE SER POSITIVO MAYOR A CERO")
    private Long productoId;

    @NotNull(message = "LA CANTIDAD ES OBLIGATORIA")
    @Positive(message = "LA CANTIDAD DEBE SER MAYOR A CERO")
    private Integer cantidad;
}