package com.jlo.prueba.pase.entities.asignations;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.Objects;
import java.util.UUID;

@Embeddable
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderAsignationId implements Serializable {

    @Column(name = "driver_id", columnDefinition = "UUID")
    private UUID driverId;

    @Column(name = "order_id", columnDefinition = "UUID")
    private UUID orderId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrderAsignationId that = (OrderAsignationId) o;
        return driverId.equals(that.driverId) && orderId.equals(that.orderId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(driverId, orderId);
    }

}
