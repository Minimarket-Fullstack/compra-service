package com.minimarket.compra_service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProveedorResponseDTO {

    private Long id;
    //private String rut;
    private String nombre;
    private String email;
    private String telefono;
    private String direccion;
}