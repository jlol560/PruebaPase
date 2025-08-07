package com.jlo.prueba.pase.dtos.asignations.responses;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderAssignationResponse {
    private String driverName;
    private boolean isActive;
    private List<OrderResponseAssignation> orders;
}
