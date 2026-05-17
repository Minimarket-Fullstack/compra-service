package com.minimarket.compra_service.service;

import com.minimarket.compra_service.client.ProveedorClient;
import com.minimarket.compra_service.dto.CompraRequestDTO;
import com.minimarket.compra_service.dto.CompraResponseDTO;
import com.minimarket.compra_service.dto.DetalleCompraRequestDTO;
import com.minimarket.compra_service.dto.DetalleCompraResponseDTO;
import com.minimarket.compra_service.exception.ProveedorNotFoundException;
import com.minimarket.compra_service.model.Compra;
import com.minimarket.compra_service.model.DetalleCompra;
import com.minimarket.compra_service.model.EstadoCompra;
import com.minimarket.compra_service.repository.CompraRepository;
import feign.FeignException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
@Slf4j
public class CompraService {

    private final CompraRepository compraRepository;

    private final ProveedorClient proveedorClient;

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

    private void valdiarProveedor(Long proveedorId){
        try{
            proveedorClient.obtenerPorId(proveedorId);
            log.info("ESPECIALIDAD {} VALIDADA CORRECTAMENTE (FEIGN CLIENT)", proveedorId);

        }catch( FeignException.NotFound e){
            throw new RuntimeException("EL PROVEEDOR CON EL ID " + proveedorId + " NO EXISTE EN PROVEEDOR-SERVICE");
        } catch (FeignException e){
            throw new ProveedorNotFoundException(proveedorId);
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

        valdiarProveedor(dto.getProveedorId());

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
