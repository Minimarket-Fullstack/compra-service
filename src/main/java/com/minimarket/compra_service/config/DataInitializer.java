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
            log.info(">>> DataInitializer: la BD ya tiene datos, se omite la carga inicial.");
            return;
        }

        log.info(">>> DataInitializer: BD vacía detectada, insertando datos de prueba...");

        Compra c1 = new Compra(null, 1L, LocalDateTime.now().minusDays(12), 85000.0, EstadoCompra.PENDIENTE, null, true);
        c1.setDetalles(List.of(
                new DetalleCompra(null, c1, 1L, 50),
                new DetalleCompra(null, c1, 2L, 75)
        ));
        compraRepository.save(c1);

        Compra c2 = new Compra(null, 2L, LocalDateTime.now().minusDays(9), 200000.0, EstadoCompra.RECIBIDA, null, true);
        c2.setDetalles(List.of(
                new DetalleCompra(null, c2, 3L, 100),
                new DetalleCompra(null, c2, 4L, 100)
        ));
        compraRepository.save(c2);

        Compra c3 = new Compra(null, 3L, LocalDateTime.now().minusDays(7), 85000.0, EstadoCompra.RECIBIDA, null, true);
        c3.setDetalles(List.of(new DetalleCompra(null, c3, 5L, 50)));
        compraRepository.save(c3);

        Compra c4 = new Compra(null, 5L, LocalDateTime.now().minusDays(5), 120000.0, EstadoCompra.PENDIENTE, null, true);
        c4.setDetalles(List.of(
                new DetalleCompra(null, c4, 6L, 80),
                new DetalleCompra(null, c4, 7L, 40)
        ));
        compraRepository.save(c4);

        Compra c5 = new Compra(null, 6L, LocalDateTime.now().minusDays(3), 95000.0, EstadoCompra.RECIBIDA, null, false);
        c5.setDetalles(List.of(new DetalleCompra(null, c5, 8L, 50)));
        compraRepository.save(c5);

        Compra c6 = new Compra(null, 8L, LocalDateTime.now().minusDays(2), 60000.0, EstadoCompra.CANCELADA, null, false);
        c6.setDetalles(List.of(new DetalleCompra(null, c6, 9L, 60)));
        compraRepository.save(c6);

        Compra c7 = new Compra(null, 9L, LocalDateTime.now().minusDays(1), 75000.0, EstadoCompra.PENDIENTE, null, true);
        c7.setDetalles(List.of(
                new DetalleCompra(null, c7, 10L, 50),
                new DetalleCompra(null, c7, 1L, 50)
        ));
        compraRepository.save(c7);

        log.info(">>> DataInitializer: {} compras insertadas.", compraRepository.count());
    }
}