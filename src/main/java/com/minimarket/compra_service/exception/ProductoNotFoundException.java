package com.minimarket.compra_service.exception;

public class ProductoNotFoundException extends RuntimeException{
    public ProductoNotFoundException(Long id){
        super("EL PRODUCTO CON EL ID " + id + " NO EXISTE EN PRODUCTO-SERVICE");
    }
}
