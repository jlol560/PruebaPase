package com.jlo.prueba.pase.dtos.drivers.requests;

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
public class DriverRequest {

    @NotBlank(message = "El nombre no puede estar vacío")
    @NotNull(message = "El nombre no puede ser nulo")
    private String name;

    @NotBlank(message = "La licencia de conducir no puede estar vacío")
    @NotNull(message = "La licencia de conducir no puede ser nulo")
    private String licenseNumber;

    private boolean active = true;
}