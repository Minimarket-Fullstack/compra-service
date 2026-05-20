# Pruebas Postman - compra-service

## GET - Listar compras
`GET http://localhost:8083/api/v1/compras`

---

## GET - Buscar por ID
`GET http://localhost:8083/api/v1/compras/1`
`GET http://localhost:8083/api/v1/compras/3`

---

## GET - Buscar por Proveedor
`GET http://localhost:8083/api/v1/compras/proveedor/1`
`GET http://localhost:8083/api/v1/compras/proveedor/5`

---

## POST - Crear compra
`POST http://localhost:8083/api/v1/compras`
```json
{
    "proveedorId": 1,
    "detalles": [
        {
            "productoId": 1,
            "nombreProducto": "Arroz 1kg",
            "cantidad": 50,
            "precioUnitario": 1200.0
        },
        {
            "productoId": 2,
            "nombreProducto": "Fideos 500g",
            "cantidad": 75,
            "precioUnitario": 800.0
        }
    ]
}
```

---

## POST - Campos vacíos (validación)
`POST http://localhost:8083/api/v1/compras`
```json
{
    "proveedorId": null,
    "detalles": null
}
```

---

## POST - Proveedor inválido (validación)
`POST http://localhost:8083/api/v1/compras`
```json
{
    "proveedorId": -1,
    "detalles": [
        {
            "productoId": 1,
            "nombreProducto": "Arroz 1kg",
            "cantidad": 10,
            "precioUnitario": 1200.0
        }
    ]
}
```

---

## PATCH - Actualizar estado
`PATCH http://localhost:8083/api/v1/compras/1/estado?estado=RECIBIDA`
`PATCH http://localhost:8083/api/v1/compras/2/estado?estado=CANCELADA`

---

## DELETE - Eliminar compra (borrado lógico)
`DELETE http://localhost:8083/api/v1/compras/3`

---

> Nota
> ```properties
> server.port=8085
> ```
