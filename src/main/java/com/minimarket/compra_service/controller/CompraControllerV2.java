package com.minimarket.compra_service.controller;

import com.minimarket.compra_service.assemblers.CompraModelAssembler;
import com.minimarket.compra_service.dto.CompraRequestDTO;
import com.minimarket.compra_service.dto.CompraResponseDTO;
import com.minimarket.compra_service.exception.CompraNotFoundException;
import com.minimarket.compra_service.model.EstadoCompra;
import com.minimarket.compra_service.service.CompraService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping("/api/v2/compras")
@RequiredArgsConstructor
@Tag(name = "Compras HATEOAS", description = "Endpoints de compras con enlaces HATEOAS")
public class CompraControllerV2 {

    private final CompraService compraService;
    private final CompraModelAssembler assembler;

    @GetMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar compras con HATEOAS")
    public CollectionModel<EntityModel<CompraResponseDTO>> listar() {
        List<EntityModel<CompraResponseDTO>> compras = compraService.obtenerTodos().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(compras,
                linkTo(methodOn(CompraControllerV2.class).listar()).withSelfRel());
    }

    @GetMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Obtener compra por ID con HATEOAS")
    public EntityModel<CompraResponseDTO> obtenerPorId(@PathVariable Long id) {
        CompraResponseDTO compra = compraService.obtenerPorId(id)
                .orElseThrow(() -> new CompraNotFoundException(id));
        return assembler.toModel(compra);
    }

    @GetMapping(value = "/proveedor/{proveedorId}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Listar compras por proveedor con HATEOAS")
    public CollectionModel<EntityModel<CompraResponseDTO>> obtenerPorProveedor(@PathVariable Long proveedorId) {
        List<EntityModel<CompraResponseDTO>> compras = compraService.obtenerPorProveedor(proveedorId).stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(compras,
                linkTo(methodOn(CompraControllerV2.class).obtenerPorProveedor(proveedorId)).withSelfRel(),
                linkTo(methodOn(CompraControllerV2.class).listar()).withRel("compras"));
    }

    @PostMapping(produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Crear compra con HATEOAS")
    public ResponseEntity<EntityModel<CompraResponseDTO>> guardarCompra(@Valid @RequestBody CompraRequestDTO dto) {
        return ResponseEntity.status(201).body(assembler.toModel(compraService.guardar(dto)));
    }

    @PatchMapping(value = "{id}/estado", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Actualizar estado de compra con HATEOAS")
    public EntityModel<CompraResponseDTO> actualizarEstado(@PathVariable Long id, @RequestParam EstadoCompra estado) {
        CompraResponseDTO compra = compraService.actualizarEstado(id, estado)
                .orElseThrow(() -> new CompraNotFoundException(id));
        return assembler.toModel(compra);
    }

    @DeleteMapping(value = "/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    @Operation(summary = "Eliminar compra")
    public ResponseEntity<Map<String, String>> eliminar(@PathVariable Long id) {
        compraService.eliminarCompra(id);
        return ResponseEntity.ok(Map.of("MENSAJE", "COMPRA ELIMINADA CORRECTAMENTE"));
    }
}
