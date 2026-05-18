package com.minimarket.compra_service.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "Producto-service", url = "${producto.service.url}")
public interface ProductoClient {
    @GetMapping("/api/productos/{id}")
    String obtenerPorId(@PathVariable Long id);
}
