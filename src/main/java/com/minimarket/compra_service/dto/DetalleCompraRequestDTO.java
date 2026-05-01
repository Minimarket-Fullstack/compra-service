package com.minimarket.compra_service.dto;

import jakarta.validation.constraints.NotBlank;
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

    @NotBlank(message = "EL NOMBRE DEL PRODUCTO ES OBLIGATORIO")
    private String nombreProducto;

    @NotNull(message = "LA CANTIDAD ES OBLIGATORIA")
    @Positive(message = "LA CANTIDAD DEBE SER MAYOR A CERO")
    private Integer cantidad;

    @NotNull(message = "EL PRECIO UNITARIO ES OBLIGATORIO")
    @Positive(message = "EL PRECIO UNITARIO DEBE SER MAYOR A CERO")
    private Double precioUnitario;


}
