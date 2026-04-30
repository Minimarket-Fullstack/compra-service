package com.minimarket.compra_service.repository;

import com.minimarket.compra_service.model.Compra;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompraRepository extends JpaRepository<Compra, Long> {
}
