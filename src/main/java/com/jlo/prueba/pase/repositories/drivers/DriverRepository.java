package com.jlo.prueba.pase.repositories.drivers;

import com.jlo.prueba.pase.entities.drivers.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface DriverRepository extends JpaRepository<Driver, UUID> {
    List<Driver> findByActiveTrue();
    Optional<Driver> findByLicenseNumber(String licenseNumber);
}
