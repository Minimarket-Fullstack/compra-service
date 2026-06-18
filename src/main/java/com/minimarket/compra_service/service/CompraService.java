package com.minimarket.compra_service.service;

import com.minimarket.compra_service.client.ProductoClient;
import com.minimarket.compra_service.client.ProveedorClient;
import com.minimarket.compra_service.dto.*;
import com.minimarket.compra_service.exception.CompraNotFoundException;
import com.minimarket.compra_service.exception.ProductoNotFoundException;
import com.minimarket.compra_service.exception.ProveedorNotFoundException;
import com.minimarket.compra_service.model.Compra;
import com.minimarket.compra_service.model.DetalleCompra;
import com.minimarket.compra_service.model.EstadoCompra;
import com.minimarket.compra_service.repository.CompraRepository;
import feign.FeignException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@Transactional
@RequiredArgsConstructor
public class CompraService {

    private final CompraRepository compraRepository;
    private final ProveedorClient proveedorClient;
    private final ProductoClient productoClient;

    private CompraResponseDTO mapToDto(Compra compra) {

        List<DetalleCompraResponseDTO> detalles = compra.getDetalles().stream()
                .map(i -> {
                    ProductoResponseDTO producto = null;
                    try {
                        producto = productoClient.obtenerPorId(i.getProductoId());
                    } catch (Exception e) {
                        log.warn("NO SE PUDO OBTENER EL PRODUCTO CON ID {}: {}", i.getProductoId(), e.getMessage());
                    }
                    return new DetalleCompraResponseDTO(i.getCantidad(), producto);
                })
                .collect(Collectors.toList());

        ProveedorResponseDTO proveedor = null;
        try {
            proveedor = proveedorClient.obtenerPorId(compra.getProveedorId());
        } catch (Exception e) {
            log.warn("NO SE PUDO OBTENER EL PROVEEDOR CON ID {}: {}", compra.getProveedorId(), e.getMessage());
        }

        return new CompraResponseDTO(
                compra.getId(),
                proveedor,
                compra.getFechaCompra(),
                compra.getTotal(),
                compra.getEstado().name(),
                detalles
        );
    }

    private ProductoResponseDTO validarProductoId(Long productoId) {
        try {
            ProductoResponseDTO producto = productoClient.obtenerPorId(productoId);
            log.info("EL PRODUCTO CON EL ID {} HA SIDO VALIDADO CORRECTAMENTE (FEIGN)", productoId);
            return producto;
        } catch (FeignException.NotFound e) {
            throw new ProductoNotFoundException(productoId);
        } catch (Exception e) {
            throw new RuntimeException("NO SE PUDO CONECTAR CON EL PRODUCTO-SERVICE: " + e.getMessage());
        }
    }

    private ProveedorResponseDTO validarProveedorId(Long proveedorId) {
        try {
            ProveedorResponseDTO proveedor = proveedorClient.obtenerPorId(proveedorId);
            log.info("EL PROVEEDOR CON EL ID {} HA SIDO VALIDADO CORRECTAMENTE (FEIGN)", proveedorId);
            return proveedor;
        } catch (FeignException.NotFound e) {
            throw new ProveedorNotFoundException(proveedorId);
        } catch (Exception e) {
            throw new RuntimeException("NO SE PUDO CONECTAR CON EL PROVEEDOR-SERVICE: " + e.getMessage());
        }
    }

    private DetalleCompra mapToDetalle(DetalleCompraRequestDTO dto, Compra compra) {
        validarProductoId(dto.getProductoId());
        return new DetalleCompra(null, compra, dto.getProductoId(), dto.getCantidad());
    }

    private Double calcularTotal(List<DetalleCompraRequestDTO> detalles) {
        return detalles.stream()
                .mapToDouble(d -> {
                    ProductoResponseDTO producto = validarProductoId(d.getProductoId());
                    return producto.getPrecio().doubleValue() * d.getCantidad();
                })
                .sum();
    }

    public List<CompraResponseDTO> obtenerTodos() {
        log.info("LISTANDO TODAS LAS COMPRAS ACTIVAS");
        return compraRepository.findByActivoTrue().stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public Optional<CompraResponseDTO> obtenerPorId(Long id) {
        return compraRepository.findByIdAndActivoTrue(id).map(this::mapToDto);
    }

    public List<CompraResponseDTO> obtenerPorProveedor(Long proveedorId) {
        validarProveedorId(proveedorId);
        return compraRepository.findByProveedorIdAndActivoTrue(proveedorId).stream()
                .map(this::mapToDto)
                .collect(Collectors.toList());
    }

    public CompraResponseDTO guardar(CompraRequestDTO dto) {
        validarProveedorId(dto.getProveedorId());
        log.info("GUARDANDO COMPRA para proveedorId={}", dto.getProveedorId());

        Compra compra = new Compra(null, dto.getProveedorId(), LocalDateTime.now(),
                0.0, EstadoCompra.PENDIENTE, null, true);

        List<DetalleCompra> detalles = dto.getDetalles().stream()
                .map(d -> mapToDetalle(d, compra))
                .toList();

        compra.setDetalles(detalles);
        compra.setTotal(calcularTotal(dto.getDetalles()));

        return mapToDto(compraRepository.save(compra));
    }

    public Optional<CompraResponseDTO> actualizarEstado(Long id, EstadoCompra estadoNuevo) {
        return compraRepository.findByIdAndActivoTrue(id).map(existente -> {
            existente.setEstado(estadoNuevo);
            return mapToDto(compraRepository.save(existente));
        });
    }

    public void eliminarCompra(Long id) {
        Compra compra = compraRepository.findByIdAndActivoTrue(id)
                .orElseThrow(() -> new CompraNotFoundException(id));
        log.info("ELIMINANDO COMPRA CON ID: {}", id);
        compra.setActivo(false);
        compraRepository.save(compra);
        log.info("COMPRA CON ID {} ELIMINADA EXITOSAMENTE", id);
    }
}