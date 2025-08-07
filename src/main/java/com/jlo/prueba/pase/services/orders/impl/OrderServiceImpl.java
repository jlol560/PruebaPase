package com.jlo.prueba.pase.services.orders.impl;

import com.jlo.prueba.pase.utils.enums.OrderStatus;
import com.jlo.prueba.pase.dtos.OrderFilter;
import com.jlo.prueba.pase.dtos.requests.OrderRequest;
import com.jlo.prueba.pase.dtos.requests.OrderRequestStatus;
import com.jlo.prueba.pase.entities.Order;
import com.jlo.prueba.pase.exceptions.orders.OrderNotFoundException;
import com.jlo.prueba.pase.mappers.OrderMapper;
import com.jlo.prueba.pase.repositories.orders.OrderRepository;
import com.jlo.prueba.pase.dtos.responses.ResponseOrder;
import com.jlo.prueba.pase.services.orders.OrderService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.UUID;

@Service
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderMapper orderMapper;

    public OrderServiceImpl(OrderRepository orderRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public ResponseOrder createOrder(OrderRequest orderRequest) {
        Order order = orderMapper.orderRequestToOrder(orderRequest);
        Order orderResult = orderRepository.save(order);
        OrderRequest orderResponse = orderMapper.orderToOrderRequest(orderResult);
        List<OrderRequest> orders = List.of(orderResponse);
        return orderMapper.toResponseOrder(orders, "Orden creada exitosamente", HttpStatus.CREATED.value());
    }

    @Override
    public ResponseOrder getOrderById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new OrderNotFoundException(id));
        OrderRequest orderResponse = orderMapper.orderToOrderRequest(order);
        List<OrderRequest> orders = List.of(orderResponse);
        return orderMapper.toResponseOrder(orders, "Orden encontrada exitosamente", HttpStatus.OK.value());
    }

    @Override
    public ResponseOrder updateStatusOrder(OrderRequestStatus orderRequest) {
        Order order = orderRepository.findById(UUID.fromString(orderRequest.getId()))
                .orElseThrow(() -> new OrderNotFoundException(orderRequest.getId()));

        String message = resolveStatusMessage(order, orderRequest);

        if (message == null) {
            order.setStatus(orderRequest.getStatus());
            Order updatedOrder = orderRepository.save(order);
            message = String.format("Se actualiza el estado de la orden %s a %s",
                    orderRequest.getId(), orderRequest.getStatus());
            return createSuccessResponse(updatedOrder, message);
        }

        return createErrorResponse(order, message);
    }

    @Override
    public ResponseOrder getFilteredOrders(OrderFilter filter, int page, int size) {
        LocalDateTime startDateTime = filter.getStartDate() != null ?
                filter.getStartDate().atStartOfDay() : null;
        LocalDateTime endDateTime = filter.getEndDate() != null ?
                filter.getEndDate().atTime(LocalTime.MAX) : null;

        Pageable pageable = PageRequest.of(page, size);

        Page<Order> pageResult = orderRepository.findWithFilters(
                filter.getStatus() != null ? filter.getStatus().name() : null,
                filter.getOrigin(),
                filter.getDestination(),
                startDateTime,
                endDateTime,
                pageable
        );

        if (pageResult.isEmpty()) {
            throw new OrderNotFoundException(buildNotFoundMessage(filter));
        }

        List<OrderRequest> orderRequests = pageResult.getContent()
                .stream()
                .map(orderMapper::orderToOrderRequest)
                .toList();

        return orderMapper.toResponseOrder(
                orderRequests,
                "Órdenes encontradas",
                HttpStatus.OK.value(),
                pageResult.getTotalElements(),
                pageResult.getTotalPages()
        );
    }

    private String resolveStatusMessage(Order order, OrderRequestStatus request) {
        return switch (order.getStatus()) {
            case CREATED -> resolveCreatedStatusMessage(order, request);

            case CANCELLED -> String.format("La orden %s ya se encuentra en estado CANCELADA", request.getId());

            case DELIVERED -> resolveDeliveredStatusMessage(order, request);

            case IN_TRANSIT -> resolveInTransitStatusMessage(order, request);
        };
    }

    private String resolveCreatedStatusMessage(Order order, OrderRequestStatus request) {
        if (request.getStatus() == OrderStatus.DELIVERED) {
            return String.format("La orden %s no puede ser ENTREGADA por que aun no esta EN TRÁNSITO", order.getId());
        }
        return request.getStatus() == OrderStatus.CREATED
                ? String.format("La orden %s ya se encuentra en estado CREADA", order.getId())
                : null;
    }

    private String resolveInTransitStatusMessage(Order order, OrderRequestStatus request) {
        if (request.getStatus() == OrderStatus.IN_TRANSIT) {
            return String.format("La orden %s ya se encuentra EN TRANSITO", order.getId());
        }
        return request.getStatus() == OrderStatus.CREATED
                ? String.format("La orden %s ya está CREADA y se encuentra en estado EN TRÁNSITO", request.getId())
                : null;
    }

    private String resolveDeliveredStatusMessage(Order order, OrderRequestStatus request) {
        if (request.getStatus() == OrderStatus.CANCELLED) {
            return String.format("La orden %s ya está ENTREGADA y no puede ser CANCELADA", order.getId());
        }
        return request.getStatus() == OrderStatus.CREATED
                || request.getStatus() == OrderStatus.IN_TRANSIT
                || request.getStatus() == OrderStatus.DELIVERED
                ? String.format("La orden %s ya se encuentra en estado ENTREGADA", order.getId())
                : null;
    }

    private ResponseOrder createSuccessResponse(Order order, String message) {
        OrderRequest response = orderMapper.orderToOrderRequest(order);
        return orderMapper.toResponseOrder(
                List.of(response),
                message,
                HttpStatus.OK.value()
        );
    }

    private ResponseOrder createErrorResponse(Order order, String message) {
        OrderRequest response = orderMapper.orderToOrderRequest(order);
        return orderMapper.toResponseOrder(
                List.of(response),
                message,
                HttpStatus.OK.value()
        );
    }

    private String buildNotFoundMessage(OrderFilter filter) {
        StringBuilder message = new StringBuilder("No se encontraron órdenes con los filtros:");

        if (filter.getStatus() != null) {
            message.append(" estado=").append(filter.getStatus());
        }
        if (filter.getOrigin() != null) {
            message.append(", origen=").append(filter.getOrigin());
        }
        if (filter.getDestination() != null) {
            message.append(", destino=").append(filter.getDestination());
        }
        if (filter.getStartDate() != null) {
            message.append(", desde=").append(filter.getStartDate());
        }
        if (filter.getEndDate() != null) {
            message.append(", hasta=").append(filter.getEndDate());
        }

        return message.toString();
    }

}
