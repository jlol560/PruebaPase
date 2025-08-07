package com.jlo.prueba.pase.mappers;

import com.jlo.prueba.pase.dtos.requests.OrderRequest;
import com.jlo.prueba.pase.dtos.responses.ResponseOrder;
import com.jlo.prueba.pase.entities.Order;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2025-08-06T23:27:51-0600",
    comments = "version: 1.6.3, compiler: javac, environment: Java 21.0.7 (Amazon.com Inc.)"
)
@Component
public class OrderMapperImpl implements OrderMapper {

    @Override
    public OrderRequest orderToOrderRequest(Order order) {
        if ( order == null ) {
            return null;
        }

        OrderRequest orderRequest = new OrderRequest();

        orderRequest.setDate( order.getCreatedAt() );
        orderRequest.setOrigin( order.getOrigin() );
        orderRequest.setDestination( order.getDestination() );
        orderRequest.setStatus( order.getStatus() );

        return orderRequest;
    }

    @Override
    public ResponseOrder toResponseOrder(List<OrderRequest> orders, String message, int httpStatusCode, long totalElements, int totalPages) {
        if ( orders == null && message == null ) {
            return null;
        }

        ResponseOrder responseOrder = new ResponseOrder();

        List<OrderRequest> list = orders;
        if ( list != null ) {
            responseOrder.setOrders( new ArrayList<OrderRequest>( list ) );
        }
        responseOrder.setMessage( message );
        responseOrder.setHttpStatusCode( httpStatusCode );
        responseOrder.setTotalElements( totalElements );
        responseOrder.setTotalPages( totalPages );

        return responseOrder;
    }

    @Override
    public Order orderRequestToOrder(OrderRequest orderRequest) {
        if ( orderRequest == null ) {
            return null;
        }

        Order order = new Order();

        order.setStatus( orderRequest.getStatus() );
        order.setCreatedAt( orderRequest.getDate() );
        order.setOrigin( orderRequest.getOrigin() );
        order.setDestination( orderRequest.getDestination() );

        return order;
    }
}
