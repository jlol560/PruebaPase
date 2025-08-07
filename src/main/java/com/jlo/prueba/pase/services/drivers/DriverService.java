package com.jlo.prueba.pase.services.drivers;

import com.jlo.prueba.pase.dtos.drivers.requests.DriverRequest;
import com.jlo.prueba.pase.dtos.drivers.responses.DriverResponse;

public interface DriverService {
    DriverResponse createDriver(DriverRequest driverRequest);
    DriverResponse findAllDriverActives();
}
