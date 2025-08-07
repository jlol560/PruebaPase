package com.jlo.prueba.pase.mappers.drivers;

import com.jlo.prueba.pase.dtos.drivers.requests.DriverRequest;
import com.jlo.prueba.pase.dtos.drivers.responses.DriverResponse;
import com.jlo.prueba.pase.entities.drivers.Driver;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-07T01:25:23-0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Amazon.com Inc.)"
)
@Component
public class DriverMapperImpl implements DriverMapper {

    @Override
    public DriverRequest driverToDriverRequest(Driver driver) {
        if ( driver == null ) {
            return null;
        }

        DriverRequest driverRequest = new DriverRequest();

        driverRequest.setLicenseNumber( trimAndUpperCaseLicenseNumber( driver.getLicenseNumber() ) );
        driverRequest.setName( driver.getName() );
        driverRequest.setActive( driver.isActive() );

        return driverRequest;
    }

    @Override
    public Driver driverRequestToDriver(DriverRequest driverRequest) {
        if ( driverRequest == null ) {
            return null;
        }

        Driver driver = new Driver();

        driver.setName( driverRequest.getName() );
        driver.setLicenseNumber( driverRequest.getLicenseNumber() );
        driver.setActive( driverRequest.isActive() );

        return driver;
    }

    @Override
    public DriverResponse toDriverResponse(List<DriverRequest> drivers, String message, int httpStatusCode) {
        if ( drivers == null && message == null ) {
            return null;
        }

        DriverResponse driverResponse = new DriverResponse();

        List<DriverRequest> list = drivers;
        if ( list != null ) {
            driverResponse.setDrivers( new ArrayList<DriverRequest>( list ) );
        }
        driverResponse.setMessage( message );
        driverResponse.setHttpStatusCode( httpStatusCode );

        return driverResponse;
    }
}
