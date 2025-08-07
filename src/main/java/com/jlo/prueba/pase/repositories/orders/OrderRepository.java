package com.jlo.prueba.pase.repositories.orders;

import com.jlo.prueba.pase.entities.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.UUID;

public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query(value = """
        SELECT * FROM orders o
            WHERE (CAST(:status AS TEXT) IS NULL OR o.status = :status)
                AND (CAST(:origin AS TEXT) IS NULL OR UPPER(o.origin) LIKE UPPER(CONCAT('%', CAST(:origin AS TEXT), '%')))
                AND (CAST(:destination AS TEXT) IS NULL OR UPPER(o.destination) LIKE UPPER(CONCAT('%', CAST(:destination AS TEXT), '%')))
                AND (CAST(:startDate AS TIMESTAMP) IS NULL OR o.created_at >= CAST(:startDate AS TIMESTAMP))
                AND (CAST(:endDate AS TIMESTAMP) IS NULL OR o.created_at <= CAST(:endDate AS TIMESTAMP))
        """,
            countQuery = """
        SELECT COUNT(*) FROM orders o
                    WHERE (CAST(:status AS TEXT) IS NULL OR o.status = :status)
                        AND (CAST(:origin AS TEXT) IS NULL OR UPPER(o.origin) LIKE UPPER(CONCAT('%', CAST(:origin AS TEXT), '%')))
                        AND (CAST(:destination AS TEXT) IS NULL OR UPPER(o.destination) LIKE UPPER(CONCAT('%', CAST(:destination AS TEXT), '%')))
                        AND (CAST(:startDate AS TIMESTAMP) IS NULL OR o.created_at >= CAST(:startDate AS TIMESTAMP))
                        AND (CAST(:endDate AS TIMESTAMP) IS NULL OR o.created_at <= CAST(:endDate AS TIMESTAMP))
        """,
            nativeQuery = true)
    Page<Order> findWithFilters(
            @Param("status") String status,
            @Param("origin") String origin,
            @Param("destination") String destination,
            @Param("startDate") LocalDateTime startDate,
            @Param("endDate") LocalDateTime endDate,
            Pageable pageable);
 }

