package com.minimarket.compra_service.service;


import com.minimarket.compra_service.dto.CompraResponseDTO;
import com.minimarket.compra_service.dto.DetalleCompraResponseDTO;
import com.minimarket.compra_service.model.Compra;
import com.minimarket.compra_service.repository.CompraRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CompraService {

    private final CompraRepository compraRepository;

    //detallecompraresponsedto
    //    private Long id;
    //    private Long productoId;
    //    private String nombreProducto;
    //    private Integer cantidad;
    //    private Double precioUnitario;
    //    private Double subtotal;


    //Compra
    //    private Long id;
    //    private Long proveedorId;
    //    private LocalDateTime fechaCompra;
    //    private Double total;
    //    private String estado;
    //    private List<DetalleCompraResponseDTO> detalles;


    public CompraResponseDTO mapToDto(Compra compra){
        List< DetalleCompraResponseDTO> detalles = compra.getDetalles().stream().
                map( i -> new DetalleCompraResponseDTO(i.getId(),
                        i.getProductoId(),
                        i.getNombreProducto(),
                        i.getCantidad(),i.getPrecioUnitario(),i.getSubtotal()))
                        .collect(Collectors.toList());
        return new CompraResponseDTO(
                compra.getId(),
                compra.getProveedorId(),
                compra.getFechaCompra(),
                compra.getTotal(),
                compra.getEstado().name(),
                detalles
        );

    }

}
