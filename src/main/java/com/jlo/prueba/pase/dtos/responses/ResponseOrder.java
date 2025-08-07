package com.jlo.prueba.pase.dtos.responses;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jlo.prueba.pase.dtos.requests.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class ResponseOrder {

    private List<OrderRequest> orders;
    private String message;
    private int httpStatusCode;
    @JsonIgnore
    private long totalElements;
    @JsonIgnore
    private int totalPages;

    public static ResponseOrder error(int httpStatusCode, String message) {
        return new ResponseOrder(null, message, httpStatusCode, 0, 0);
    }

}
