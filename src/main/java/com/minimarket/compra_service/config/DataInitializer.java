package com.minimarket.compra_service.config;

import com.minimarket.compra_service.repository.CompraRepository;
import com.minimarket.compra_service.repository.DetalleCompraRepository;
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




        log.info(">>> DataInitializer: {} compras insertadas.", compraRepository.count());

    }


}
