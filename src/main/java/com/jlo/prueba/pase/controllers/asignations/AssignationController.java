package com.jlo.prueba.pase.controllers.asignations;

import com.jlo.prueba.pase.dtos.asignations.responses.AssignationResponse;
import com.jlo.prueba.pase.services.asignations.AssignationsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.io.IOException;


@RestController
@RequestMapping("/api/assignations")
@Tag(name = "Asignaciones", description = "API para gestionar asignaciones de órdenes a conductores")
public class AssignationController {

    private final AssignationsService assignationsService;

    public AssignationController(AssignationsService assignationsService) {
        this.assignationsService = assignationsService;
    }

    @PostMapping
    @Operation(
            summary = "Asignar una orden a un conductor",
            description = "Asigna una orden a un conductor y guarda archivos PDF e imagen.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Orden asignada correctamente"),
                    @ApiResponse(responseCode = "400", description = "Error de validación en los parámetros"),
                    @ApiResponse(responseCode = "404", description = "Conductor u orden no encontrados")
            }
    )
    public ResponseEntity<AssignationResponse> assignOrderToDriver(
            @RequestParam("driverId")
            @Parameter(description = "ID del conductor (UUID)", example = "550e8400-e29b-41d4-a716-446655440000")
            String driverId,
            @RequestParam("orderId")
            @Parameter(description = "ID de la orden (UUID)", example = "123e4567-e89b-12d3-a456-426614174000")
            String orderId,
            @RequestParam("pdfFile")
            @Parameter(description = "Archivo PDF de la orden")
            MultipartFile pdfFile,
            @RequestParam("imageFile")
            @Parameter(description = "Imagen de la orden")
            MultipartFile imageFile) throws IOException {

        AssignationResponse response = assignationsService.assignOrderToDriver(
                driverId, orderId, pdfFile, imageFile);

        return ResponseEntity.status(response.getHttpStatusCode()).body(response);
    }

}
