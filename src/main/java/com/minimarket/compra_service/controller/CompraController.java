package com.minimarket.compra_service.controller;


import com.minimarket.compra_service.dto.CompraRequestDTO;
import com.minimarket.compra_service.dto.CompraResponseDTO;
import com.minimarket.compra_service.model.Compra;
import com.minimarket.compra_service.model.EstadoCompra;
import com.minimarket.compra_service.service.CompraService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.apache.coyote.Response;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping("/{id}")
    public ResponseEntity<CompraResponseDTO> obtenerPorId(@PathVariable Long id){
        return compraService.obtenerPorId(id).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/proveedor/{proveedorId}")
    public ResponseEntity<List<CompraResponseDTO>> obtenerPorProveedor(@PathVariable Long proveedorId){
        return ResponseEntity.ok(compraService.obtenerPorProveedor(proveedorId));
    }

    @PostMapping
    public ResponseEntity<CompraResponseDTO> guardarCompra(@Valid @RequestBody CompraRequestDTO dto){
        return ResponseEntity.status(201).body(compraService.guardar(dto));
    }

    //no put, pero si patch, pq usamos un puro dato nomás, mejor un requestParam
    @PatchMapping("{id}/estado")
    public ResponseEntity<CompraResponseDTO> actualizarEstado(@PathVariable Long id, @RequestParam EstadoCompra estado){
        return compraService.actualizarEstado(id,estado).map(ResponseEntity::ok).orElse(ResponseEntity.notFound().build());
    }

    // eliminar
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Long id){
        compraService.eliminarCompra(id);
        return ResponseEntity.noContent().build();
    }







}
