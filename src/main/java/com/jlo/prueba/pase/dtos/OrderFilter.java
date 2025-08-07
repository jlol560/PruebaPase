package com.jlo.prueba.pase.dtos;

import com.jlo.prueba.pase.utils.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderFilter {
    private OrderStatus status;
    private String origin;
    private String destination;
    private LocalDate startDate;
    private LocalDate endDate;
}
