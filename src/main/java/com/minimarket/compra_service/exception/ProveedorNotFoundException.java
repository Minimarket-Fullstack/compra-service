package com.minimarket.compra_service.exception;

public class ProveedorNotFoundException extends RuntimeException{
    public ProveedorNotFoundException(Long id){
        super("EL PROVEEDOR CON EL ID " + id + " NO EXISTE EN PROVEEDOR-SERVICE");
    }
}
