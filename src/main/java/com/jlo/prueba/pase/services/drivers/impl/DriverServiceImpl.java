package com.jlo.prueba.pase.services.drivers.impl;

import com.jlo.prueba.pase.dtos.drivers.requests.DriverRequest;
import com.jlo.prueba.pase.dtos.drivers.responses.DriverResponse;
import com.jlo.prueba.pase.entities.drivers.Driver;
import com.jlo.prueba.pase.exceptions.drivers.DriverNotFoundException;
import com.jlo.prueba.pase.exceptions.drivers.LicenseNumberException;
import com.jlo.prueba.pase.mappers.drivers.DriverMapper;
import com.jlo.prueba.pase.repositories.drivers.DriverRepository;
import com.jlo.prueba.pase.services.drivers.DriverService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DriverServiceImpl implements DriverService {

    private final DriverRepository driverRepository;
    private final DriverMapper driverMapper;

    public DriverServiceImpl(DriverRepository driverRepository, DriverMapper driverMapper) {
        this.driverRepository = driverRepository;
        this.driverMapper = driverMapper;
    }

    @Override
    public DriverResponse createDriver(DriverRequest driverRequest) {
        Driver driver = driverMapper.driverRequestToDriver(driverRequest);

        Optional<Driver> existingDriver = driverRepository.findByLicenseNumber(driver.getLicenseNumber());
        if (existingDriver.isPresent()) {
            throw new LicenseNumberException(driverRequest.getLicenseNumber());
        }

        Driver createdDriver = driverRepository.save(driver);
        DriverRequest driverResult = driverMapper.driverToDriverRequest(createdDriver);
        List<DriverRequest> drivers = List.of(driverResult);
        return driverMapper.toDriverResponse(drivers, "Conductor creado exitosamente", HttpStatus.CREATED.value());
    }

    @Override
    public DriverResponse findAllDriverActives() {
        List<Driver> drivers = driverRepository.findByActiveTrue();
        if (drivers.isEmpty()) {
            throw new DriverNotFoundException("No se encontraron conductores activos");
        }
        List<DriverRequest> driverRequests = drivers.stream()
                .map(driverMapper::driverToDriverRequest)
                .toList();
        return driverMapper.toDriverResponse(driverRequests
                , "Conductores activos encontrados", HttpStatus.OK.value());
    }

}
