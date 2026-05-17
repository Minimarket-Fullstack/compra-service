package com.minimarket.compra_service.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "compra")
public class Compra {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false) // unirlo con el proveedor
    private Long proveedorId;

    //fecha + hora_hora,minutos,segundos
    @Column(nullable = false)
    private LocalDateTime fechaCompra;

    @Column(nullable = false)
    private Double total;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private EstadoCompra estado;

    @OneToMany(mappedBy = "compra", cascade = CascadeType.ALL)
    private List<DetalleCompra> detalles = new ArrayList<>();

    private boolean activo = true;

    public void calcularTotal(){
        if(detalles == null || detalles.isEmpty()){
            total=0.0;
        } else{
            total = detalles.stream().mapToDouble(DetalleCompra::getSubtotal).sum();
        }
    }
}
