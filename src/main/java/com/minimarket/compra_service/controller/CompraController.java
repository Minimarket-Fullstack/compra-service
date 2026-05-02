package com.minimarket.compra_service.controller;


import com.minimarket.compra_service.dto.CompraResponseDTO;
import com.minimarket.compra_service.service.CompraService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("api/v1/compras")
@RequiredArgsConstructor
public class CompraController {

    private final CompraService compraService;

    @GetMapping
    public ResponseEntity<List<CompraResponseDTO>> listar(){
        return ResponseEntity.ok(compraService.obtenerTodos());
    }


}
