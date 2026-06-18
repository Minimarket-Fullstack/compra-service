package com.minimarket.compra_service.dto;

// para entrada lo q se envia desde el postman<

// validaciones van aquí
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraRequestDTO {

    @NotNull(message = "EL ID DEL PROVEEDOR ES OBLIGATORIO")
    @Positive(message = "EL ID DEL PROVEEDOR DEBE SER MAYOR A CERO")
    private Long proveedorId;

    @NotNull(message = "LOS DETALLES SON OBLIGATORIOS")
    private List<DetalleCompraRequestDTO> detalles;
}
