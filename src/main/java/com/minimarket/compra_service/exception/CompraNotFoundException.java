package com.minimarket.compra_service.exception;

public class CompraNotFoundException extends RuntimeException {
    public CompraNotFoundException(Long id) {
        super("Compra no encontrada con id: " + id);
    }
}
