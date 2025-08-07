package com.jlo.prueba.pase.mappers.drivers;

import com.jlo.prueba.pase.dtos.drivers.requests.DriverRequest;
import com.jlo.prueba.pase.dtos.drivers.responses.DriverResponse;
import com.jlo.prueba.pase.entities.drivers.Driver;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;

@Mapper(componentModel = "spring")
public interface DriverMapper {

    DriverRequest driverToDriverRequest(Driver driver);

    @Mapping(target = "licenseNumber", source = "licenseNumber", qualifiedByName = "trimAndUpperCase")
    Driver driverRequestToDriver(DriverRequest driverRequest);

    @Mapping(target = "drivers", source = "drivers")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "httpStatusCode", source = "httpStatusCode")
    DriverResponse toDriverResponse(List<DriverRequest> drivers, String message, int httpStatusCode);

    @Named("trimAndUpperCase")
    default String trimAndUpperCaseLicenseNumber(String licenseNumber) {
        return licenseNumber != null ? licenseNumber.trim().toUpperCase() : null;
    }
}
