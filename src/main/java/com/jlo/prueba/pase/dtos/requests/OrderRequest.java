package com.jlo.prueba.pase.dtos.requests;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.jlo.prueba.pase.utils.enums.OrderStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Solicitud para crear una orden")
public class OrderRequest {

    @NotBlank(message = "El origen no puede estar vacío")
    @NotNull(message = "El origen no puede ser nulo")
    @Schema(description = "Origen de la orden", example = "Calle Falsa 123")
    private String origin;

    @NotBlank(message = "El destino no puede estar vacío")
    @NotNull(message = "El destino no puede ser nulo")
    @Schema(description = "Destino de la orden", example = "Avenida Siempreviva 742")
    private String destination;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm")
    @Schema(description = "Fecha y hora de creación (formato: yyyy-MM-dd HH:mm)", example = "2025-12-31 14:30")
    private LocalDateTime date;

    private OrderStatus status = OrderStatus.CREATED;
}
