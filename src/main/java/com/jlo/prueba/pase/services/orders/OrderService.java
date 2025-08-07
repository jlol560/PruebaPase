package com.jlo.prueba.pase.services.orders;

import com.jlo.prueba.pase.dtos.OrderFilter;
import com.jlo.prueba.pase.dtos.requests.OrderRequest;
import com.jlo.prueba.pase.dtos.requests.OrderRequestStatus;
import com.jlo.prueba.pase.dtos.responses.ResponseOrder;

import java.util.UUID;

public interface OrderService {
    ResponseOrder createOrder(OrderRequest orderRequest);
    ResponseOrder getOrderById(UUID id);
    ResponseOrder updateStatusOrder(OrderRequestStatus orderRequest);
    ResponseOrder getFilteredOrders(OrderFilter filter, int page, int size);
}
