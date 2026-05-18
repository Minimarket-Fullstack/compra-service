package com.minimarket.compra_service.service;

import com.minimarket.compra_service.dto.CompraRequestDTO;
import com.minimarket.compra_service.dto.CompraResponseDTO;
import com.minimarket.compra_service.dto.DetalleCompraRequestDTO;
import com.minimarket.compra_service.dto.DetalleCompraResponseDTO;
import com.minimarket.compra_service.model.Compra;
import com.minimarket.compra_service.model.DetalleCompra;
import com.minimarket.compra_service.model.EstadoCompra;
import com.minimarket.compra_service.repository.CompraRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientResponseException;

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
    private final WebClient webClient;

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

    public void validarProveedorId(Long proveedorId){
        try{
            webClient.get().uri("/api/v1/proveedores/{id}", proveedorId).retrieve().bodyToMono(String.class).block();
            log.info("EL PROVEEDOR CON EL ID {} HA SIDO VALIDADO CORRECAMENTE(WEBCLIENT)", proveedorId);

        }catch (WebClientResponseException.NotFound we){
            throw new RuntimeException("EL PROVEEDOR CON EL ID: " + proveedorId + "NO EXISTE EN EL PROVEEDOR-SERVICE");
        }catch(Exception e){
            throw new RuntimeException("NO SE PUDO CONECTAR CON EL PROVEEDOR-SERVICE: " + e.getMessage());
        }
    }

    //detalles
    public DetalleCompra mapToDetalle(DetalleCompraRequestDTO dto, Compra compra){
        //id, compra, productoId, nombreProducto,cantidad,preciounitario,subtotal
        double subtotal = dto.getCantidad() * dto.getPrecioUnitario();
        return new DetalleCompra(null,compra,dto.getProductoId(),dto.getNombreProducto(),dto.getCantidad(),dto.getPrecioUnitario(), subtotal);
    }

    public List<CompraResponseDTO> obtenerTodos(){
        return compraRepository.findByActivoTrue().stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public Optional<CompraResponseDTO> obtenerPorId(Long id){
        return compraRepository.findByIdAndActivoTrue(id).map(this::mapToDto);
    }

    public List<CompraResponseDTO> obtenerPorProveedor(Long proveedorId){
        return compraRepository.findByProveedorIdAndActivoTrue(proveedorId).stream().map(this::mapToDto).collect(Collectors.toList());
    }

    public CompraResponseDTO guardar (CompraRequestDTO dto){

        validarProveedorId(dto.getProveedorId());

        Compra compra = new Compra(null, dto.getProveedorId(), LocalDateTime.now(), 0.0, EstadoCompra.PENDIENTE, null, true);
        List<DetalleCompra> detalles = dto.getDetalles().stream().map( d -> mapToDetalle(d,compra)).toList();
        compra.setDetalles(detalles);
        compra.calcularTotal();
        return mapToDto(compraRepository.save(compra));
    }

    //actualziar
    public Optional<CompraResponseDTO> actualizarEstado(Long id, EstadoCompra estadoNuevo){
        return compraRepository.findByIdAndActivoTrue(id).map(
                existente -> {
                    existente.setEstado(estadoNuevo);
                    return mapToDto(compraRepository.save(existente));
                });
    }

    public void eliminarCompra(Long id) {
        Compra compra = compraRepository.findById(id).orElseThrow(() -> new RuntimeException("Compra no encontrada con: " + id));
        compra.setActivo(false);
        compraRepository.save(compra);
    }


}
