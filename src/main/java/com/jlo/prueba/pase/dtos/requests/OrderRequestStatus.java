package com.jlo.prueba.pase.dtos.requests;

import com.jlo.prueba.pase.utils.enums.OrderStatus;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class OrderRequestStatus {

    @NotNull(message = "El ID de la orden no puede ser nulo")
    @NotBlank(message = "El ID de la orden no puede estar vacío")
    private String id;

    @NotNull(message = "El status de la orden no puede ser nulo")
    private OrderStatus status;
}
