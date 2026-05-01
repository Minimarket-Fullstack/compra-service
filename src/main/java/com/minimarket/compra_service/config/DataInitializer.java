package com.minimarket.compra_service.config;

import com.minimarket.compra_service.model.Compra;
import com.minimarket.compra_service.model.DetalleCompra;
import com.minimarket.compra_service.model.EstadoCompra;
import com.minimarket.compra_service.repository.CompraRepository;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final CompraRepository compraRepository;

    @Override
    public void run(String... args) {
        if (compraRepository.count() > 0) {
            log.info(
                ">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial."
            );
            return;
        }

        log.info(
            ">>> DataInitializer: BD vacía detectada, insertando datos de prueba..."
        );

        //primera compra
        //id, proveedorId, fechaCompra, total, EstadoCompra(enum), LSIT<DETALLEcOMPRA>, ACTIVO(BOOL)
        Compra c1 = new Compra(null, 1L, LocalDateTime.now().minusDays(12), 85000.0, EstadoCompra.PENDIENTE, null, true);
        //id, compra, productoId, nombreProudcot, Cantidad, precioUnitario, subtotal
        DetalleCompra d1A = new DetalleCompra(null, c1, 1L,"Arroz 1kg",50, 1200.0, 60000.0);
        DetalleCompra d1B = new DetalleCompra(null, c1, 2L, "Fideos 500g", 75, 1200.0 , 90000.0);
        c1.setDetalles(List.of(d1A, d1B));
        compraRepository.save(c1);

        Compra c2 = new Compra(null, 2L, LocalDateTime.now().minusDays(9), 200000.0, EstadoCompra.RECIBIDA, null, true);
        DetalleCompra d2A = new DetalleCompra(null, c2, 3L, "Leche 1L",    100, 1000.0, 100000.0);
        DetalleCompra d2B = new DetalleCompra(null, c2, 4L, "Yogurt 200g", 100, 1000.0, 100000.0);
        c2.setDetalles(List.of(d2A, d2B));
        compraRepository.save(c2);

        // Compra 3
        Compra c3 = new Compra(null, 3L, LocalDateTime.now().minusDays(7), 85000.0, EstadoCompra.RECIBIDA, null, true);
        DetalleCompra d3A = new DetalleCompra(null, c3, 5L, "Queso Laminado", 50, 1700.0, 85000.0);
        c3.setDetalles(List.of(d3A));
        compraRepository.save(c3);

        // Compra 4
        Compra c4 = new Compra(null, 5L, LocalDateTime.now().minusDays(5), 120000.0, EstadoCompra.PENDIENTE, null, true);
        DetalleCompra d4A = new DetalleCompra(null, c4, 6L, "Papas Fritas 200g", 80,  900.0,  72000.0);
        DetalleCompra d4B = new DetalleCompra(null, c4, 7L, "Galletas 300g",     40, 1200.0,  48000.0);
        c4.setDetalles(List.of(d4A, d4B));
        compraRepository.save(c4);

        // Compra 5
        Compra c5 = new Compra(null, 6L, LocalDateTime.now().minusDays(3), 95000.0, EstadoCompra.RECIBIDA, null, false);
        DetalleCompra d5A = new DetalleCompra(null, c5, 8L, "Detergente 1kg", 50, 1900.0, 95000.0);
        c5.setDetalles(List.of(d5A));
        compraRepository.save(c5);


        Compra c6 = new Compra(null, 8L, LocalDateTime.now().minusDays(2), 60000.0, EstadoCompra.CANCELADA, null, false);
        DetalleCompra d6A = new DetalleCompra(null, c6, 9L, "Pan Molde", 60, 1000.0, 60000.0);
        c6.setDetalles(List.of(d6A));
        compraRepository.save(c6);


        Compra c7 = new Compra(null, 9L, LocalDateTime.now().minusDays(1), 75000.0, EstadoCompra.PENDIENTE, null, true);
        DetalleCompra d7A = new DetalleCompra(null, c7, 10L, "Tomates 1kg", 50, 800.0, 40000.0);
        DetalleCompra d7B = new DetalleCompra(null, c7,  1L, "Papas 1kg",   50, 700.0, 35000.0);
        c7.setDetalles(List.of(d7A, d7B));
        compraRepository.save(c7);

        //LocalDateTime.now().minusDays(12);

        log.info(
            ">>> DataInitializer: {} compras insertadas.",
            compraRepository.count()
        );
    }
}
