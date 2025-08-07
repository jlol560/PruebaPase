package com.jlo.prueba.pase.controllers.orders;

import com.jlo.prueba.pase.utils.enums.OrderStatus;
import com.jlo.prueba.pase.dtos.OrderFilter;
import com.jlo.prueba.pase.dtos.requests.OrderRequest;
import com.jlo.prueba.pase.dtos.requests.OrderRequestStatus;
import com.jlo.prueba.pase.dtos.responses.ResponseOrder;
import com.jlo.prueba.pase.services.orders.OrderService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.UUID;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderService orderService;

    public OrderController(OrderService orderService) {
        this.orderService = orderService;
    }

    @PostMapping
    public ResponseEntity<ResponseOrder> createOrder(@Valid @RequestBody OrderRequest orderRequest) {
        ResponseOrder response = orderService.createOrder(orderRequest);
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseOrder> getOrderById(@PathVariable String id) {
        ResponseOrder response = orderService.getOrderById(UUID.fromString(id));
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }

    @PatchMapping("/status")
    public ResponseEntity<ResponseOrder> updateOrderStatus(@Valid @RequestBody OrderRequestStatus orderRequestStatus) {
        ResponseOrder response = orderService.updateStatusOrder(orderRequestStatus);
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }

    @GetMapping("/filter")
    public ResponseEntity<ResponseOrder> getFilteredOrders(
            @RequestParam(required = false) OrderStatus status,
            @RequestParam(required = false) String origin,
            @RequestParam(required = false) String destination,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        OrderFilter filter = new OrderFilter(status, origin, destination, startDate, endDate);
        ResponseOrder response = orderService.getFilteredOrders(filter, page, size);
        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }
}
