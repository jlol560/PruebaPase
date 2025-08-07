package com.jlo.prueba.pase.dtos.asignations.responses;

import com.jlo.prueba.pase.dtos.requests.OrderRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class OrderResponseAssignation {
    private OrderRequest orderRequest;
    private String filePathPdf;
    private String filePathImage;
}
