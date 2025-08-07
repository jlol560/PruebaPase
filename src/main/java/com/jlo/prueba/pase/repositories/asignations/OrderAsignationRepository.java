package com.jlo.prueba.pase.repositories.asignations;

import com.jlo.prueba.pase.entities.asignations.OrdeAsignation;
import com.jlo.prueba.pase.entities.asignations.OrderAsignationId;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface OrderAsignationRepository extends JpaRepository<OrdeAsignation, OrderAsignationId> {

    Optional<OrdeAsignation> findByOrderId(UUID orderId);

}
