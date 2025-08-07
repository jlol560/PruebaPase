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
public class AssignationResponse {
    private List<OrderAssignationResponse> assignations;
    private String message;
    private int httpStatusCode;

    public static AssignationResponse error(int httpStatusCode, String message) {
        return new AssignationResponse(null, message, httpStatusCode);
    }
}
