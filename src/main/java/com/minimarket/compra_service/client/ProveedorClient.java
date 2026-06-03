package com.minimarket.compra_service.client;

import com.minimarket.compra_service.dto.ProveedorResponseDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="proveedor-service", url= "${proveedor.service.url}")
public interface ProveedorClient {
    @GetMapping("/api/v1/proveedores/{id}")
    ProveedorResponseDTO obtenerPorId(@PathVariable Long id);

}
