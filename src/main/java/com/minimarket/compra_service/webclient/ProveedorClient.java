package com.minimarket.compra_service.webclient;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Component
public class ProveedorClient {

    private final WebClient webClient;

    public ProveedorClient(@Value("${proveedor-service.url}") String proveedorServidor){
        this.webClient = WebClient.builder().baseUrl(proveedorServidor).build();
    }

    public Map<String, String> obtenerProveedorId(Long id){
        return this.webClient.get().uri("/{id}", id).retrieve()
                .onStatus(status -> status.is4xxClientError(),
                        response -> response.bodyToMono(String.class)
                                .map(body -> new RuntimeException("PROVEEDOR NO ENCONTRADO")))
                .bodyToMono(new ParameterizedTypeReference<Map<String, String>>() {})
                .block();
    }

}