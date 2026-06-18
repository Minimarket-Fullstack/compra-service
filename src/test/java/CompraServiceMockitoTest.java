import com.minimarket.compra_service.client.ProductoClient;
import com.minimarket.compra_service.client.ProveedorClient;
import com.minimarket.compra_service.dto.CompraResponseDTO;
import com.minimarket.compra_service.model.Compra;
import com.minimarket.compra_service.model.EstadoCompra;
import com.minimarket.compra_service.repository.CompraRepository;
import com.minimarket.compra_service.service.CompraService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraServiceMockitoTest {

    @Mock
    private CompraRepository compraRepository;
    @Mock
    private ProveedorClient proveedorClient;
    @Mock
    private ProductoClient productoClient;

    @InjectMocks
    private CompraService compraService;

    @Test
    void obtenerTodos_deberiaRetornarComprasActivas() {
        Compra compra = new Compra(1L, 1L, LocalDateTime.now(), 5000.0, EstadoCompra.PENDIENTE, new ArrayList<>(), true);
        when(compraRepository.findByActivoTrue()).thenReturn(List.of(compra));

        List<CompraResponseDTO> resultado = compraService.obtenerTodos();

        assertEquals(1, resultado.size());
        assertEquals(5000.0, resultado.get(0).getTotal());
        verify(compraRepository).findByActivoTrue();
    }

    @Test
    void obtenerPorId_deberiaRetornarCompraActiva() {
        Compra compra = new Compra(1L, 1L, LocalDateTime.now(), 5000.0, EstadoCompra.PENDIENTE, new ArrayList<>(), true);
        when(compraRepository.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(compra));

        Optional<CompraResponseDTO> resultado = compraService.obtenerPorId(1L);

        assertTrue(resultado.isPresent());
        assertEquals("PENDIENTE", resultado.get().getEstado());
        verify(compraRepository).findByIdAndActivoTrue(1L);
    }

    @Test
    void actualizarEstado_deberiaCambiarEstado() {
        Compra compra = new Compra(1L, 1L, LocalDateTime.now(), 5000.0, EstadoCompra.PENDIENTE, new ArrayList<>(), true);
        when(compraRepository.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(compra));
        when(compraRepository.save(any(Compra.class))).thenAnswer(inv -> inv.getArgument(0));

        Optional<CompraResponseDTO> resultado = compraService.actualizarEstado(1L, EstadoCompra.RECIBIDA);

        assertTrue(resultado.isPresent());
        assertEquals("RECIBIDA", resultado.get().getEstado());
        verify(compraRepository).save(any(Compra.class));
    }

@Test
    void obtenerPorId_deberiaRetornarVacioSiNoExiste() {
        when(compraRepository.findByIdAndActivoTrue(99L)).thenReturn(Optional.empty());

        Optional<CompraResponseDTO> resultado = compraService.obtenerPorId(99L);

        assertTrue(resultado.isEmpty());
        verify(compraRepository).findByIdAndActivoTrue(99L);
    }

    @Test
    void actualizarEstado_deberiaRetornarVacioSiCompraNoExiste() {
        when(compraRepository.findByIdAndActivoTrue(99L)).thenReturn(Optional.empty());

        Optional<CompraResponseDTO> resultado = compraService.actualizarEstado(99L, EstadoCompra.RECIBIDA);

        assertTrue(resultado.isEmpty());
        verify(compraRepository, never()).save(any(Compra.class));
    }

    @Test
    void eliminarCompra_deberiaRealizarBorradoLogico() {
        Compra compra = new Compra(1L, 1L, LocalDateTime.now(), 5000.0, EstadoCompra.PENDIENTE, new ArrayList<>(), true);
        when(compraRepository.findByIdAndActivoTrue(1L)).thenReturn(Optional.of(compra));

        compraService.eliminarCompra(1L);

        assertFalse(compra.isActivo());
        verify(compraRepository).save(compra);
    }

    @Test
    void eliminarCompra_deberiaLanzarExcepcionSiNoExiste() {
        when(compraRepository.findByIdAndActivoTrue(99L)).thenReturn(Optional.empty());

        assertThrows(RuntimeException.class, () -> compraService.eliminarCompra(99L));
        verify(compraRepository, never()).save(any(Compra.class));
    }
}
