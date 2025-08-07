package com.jlo.prueba.pase.dtos.drivers.responses;

import com.jlo.prueba.pase.dtos.drivers.requests.DriverRequest;
import com.jlo.prueba.pase.entities.drivers.Driver;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class DriverResponse {
    private List<DriverRequest> drivers;
    private String message;
    private int httpStatusCode;

    public static DriverResponse error(int httpStatusCode, String message) {
        return new DriverResponse(null, message, httpStatusCode);
    }
}
