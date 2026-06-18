package com.minimarket.compra_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DetalleCompraResponseDTO {

    private Integer cantidad;
    private ProductoResponseDTO prodcuto;
}
