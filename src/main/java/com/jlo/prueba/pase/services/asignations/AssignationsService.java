package com.jlo.prueba.pase.services.asignations;

import com.jlo.prueba.pase.dtos.asignations.responses.AssignationResponse;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface AssignationsService {
    AssignationResponse assignOrderToDriver(String driverId, String orderId
            , MultipartFile pdfFile, MultipartFile imageFile) throws IOException;
}
