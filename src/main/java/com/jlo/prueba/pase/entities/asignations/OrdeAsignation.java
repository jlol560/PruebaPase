package com.jlo.prueba.pase.entities.asignations;

import com.jlo.prueba.pase.entities.Order;
import com.jlo.prueba.pase.entities.drivers.Driver;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "orders_asignations",
        uniqueConstraints = @UniqueConstraint(columnNames = {"order_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrdeAsignation {

    @EmbeddedId
    private OrderAsignationId id;

    @MapsId("driverId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "driver_id", nullable = false)
    private Driver driver;

    @MapsId("orderId")
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "order_id", nullable = false, unique = true)
    private Order order;

    @Column(name = "file_path_pdf", nullable = false)
    private String filePathPdf;

    @Column(name = "file_path_image", nullable = false)
    private String filePathImage;

}
