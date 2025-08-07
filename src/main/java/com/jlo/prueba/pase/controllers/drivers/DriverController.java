package com.jlo.prueba.pase.controllers.drivers;

import com.jlo.prueba.pase.dtos.drivers.requests.DriverRequest;
import com.jlo.prueba.pase.dtos.drivers.responses.DriverResponse;
import com.jlo.prueba.pase.services.drivers.DriverService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/drivers")
public class DriverController {

    private final DriverService driverService;

    public DriverController(DriverService driverService) {
        this.driverService = driverService;
    }

    @PostMapping
    public ResponseEntity<DriverResponse> createDriver(@Valid @RequestBody DriverRequest driverRequest) {
        DriverResponse response = driverService.createDriver(driverRequest);
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }

    @GetMapping("/active")
    public ResponseEntity<DriverResponse> findAllActiveDrivers() {
        DriverResponse response = driverService.findAllDriverActives();
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }
}
