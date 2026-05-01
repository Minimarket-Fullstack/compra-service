package com.minimarket.compra_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class CompraResponseDTO {

    private Long id;
    private Long proveedorId;
    private LocalDateTime fechaCompra;
    private Double total;
    private String estado;
    private List<DetalleCompraResponseDTO> detalles;
}
