package com.minimarket.compra_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProductoResponseDTO {

    private Long id;
    private BigDecimal precio;
    private String descripcion;


}
