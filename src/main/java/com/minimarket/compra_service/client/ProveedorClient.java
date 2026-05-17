package com.minimarket.compra_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name ="proveedor-service", url= "${ms.especialidades.url}")
public interface ProveedorClient {
    @GetMapping("/api/v1/proveedores/{id}")
    String obtenerPorId(@PathVariable Long id);

}
