package com.minimarket.compra_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

//salida

// hacer un response para conectarlo con proveedor y producto
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponseDTO {

    private Long id;
    private ProveedorResponseDTO proveedor;
    private LocalDateTime fechaCompra;
    private Double total;
    private String estado;
    private List<DetalleCompraResponseDTO> detalles;
}
