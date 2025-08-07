package com.jlo.prueba.pase.mappers;

import com.jlo.prueba.pase.dtos.requests.OrderRequest;
import com.jlo.prueba.pase.entities.Order;
import com.jlo.prueba.pase.dtos.responses.ResponseOrder;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrderMapper {

    @Mapping(target = "date", source = "createdAt", dateFormat = "yyyy-MM-dd HH:mm")
    OrderRequest orderToOrderRequest(Order order);

    @Mapping(target = "orders", source = "orders")
    @Mapping(target = "message", source = "message")
    @Mapping(target = "httpStatusCode", source = "httpStatusCode")
    @Mapping(target = "totalElements", source = "totalElements")
    @Mapping(target = "totalPages", source = "totalPages")
    ResponseOrder toResponseOrder(List<OrderRequest> orders, String message, int httpStatusCode, long totalElements, int totalPages);

    default ResponseOrder toResponseOrder(List<OrderRequest> orders, String message, int httpStatusCode) {
        return toResponseOrder(orders, message, httpStatusCode, orders.size(), 1);
    }

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdAt", source = "date")
    @Mapping(target = "updatedAt", ignore = true)
    @Mapping(target = "origin", source = "origin")
    @Mapping(target = "destination", source = "destination")
    Order orderRequestToOrder(OrderRequest orderRequest);
}
