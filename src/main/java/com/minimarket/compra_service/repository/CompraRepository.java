package com.minimarket.compra_service.repository;

import com.minimarket.compra_service.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CompraRepository extends JpaRepository<Compra, Long> {

    List<Compra> findByActivoTrue();

    Optional<Compra> findByIdAndActivoTrue(Long id);

    List<Compra> findByProveedorIdAndActivoTrue(Long proveedorId);
}
