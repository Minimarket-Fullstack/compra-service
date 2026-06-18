package com.minimarket.compra_service.assemblers;

import com.minimarket.compra_service.controller.CompraControllerV2;
import com.minimarket.compra_service.dto.CompraResponseDTO;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Component
public class CompraModelAssembler implements RepresentationModelAssembler<CompraResponseDTO, EntityModel<CompraResponseDTO>> {

    @Override
    public EntityModel<CompraResponseDTO> toModel(CompraResponseDTO compra) {
        EntityModel<CompraResponseDTO> model = EntityModel.of(compra,
                linkTo(methodOn(CompraControllerV2.class).obtenerPorId(compra.getId())).withSelfRel(),
                linkTo(methodOn(CompraControllerV2.class).listar()).withRel("compras"));

        if (compra.getProveedor() != null && compra.getProveedor().getId() != null) {
            model.add(linkTo(methodOn(CompraControllerV2.class).obtenerPorProveedor(compra.getProveedor().getId())).withRel("compras-por-proveedor"));
        }
        return model;
    }
}
