package com.jlo.prueba.pase.services.asignations.impl;

import com.jlo.prueba.pase.exceptions.asignations.FormatIdException;
import com.jlo.prueba.pase.utils.enums.OrderStatus;
import com.jlo.prueba.pase.dtos.asignations.responses.AssignationResponse;
import com.jlo.prueba.pase.dtos.asignations.responses.OrderAssignationResponse;
import com.jlo.prueba.pase.dtos.asignations.responses.OrderResponseAssignation;
import com.jlo.prueba.pase.dtos.requests.OrderRequest;
import com.jlo.prueba.pase.entities.Order;
import com.jlo.prueba.pase.entities.asignations.OrdeAsignation;
import com.jlo.prueba.pase.entities.asignations.OrderAsignationId;
import com.jlo.prueba.pase.entities.drivers.Driver;
import com.jlo.prueba.pase.exceptions.asignations.DirectoryCreationException;
import com.jlo.prueba.pase.exceptions.asignations.FilesEmptyException;
import com.jlo.prueba.pase.exceptions.asignations.OrderIsAlreadyAssignedException;
import com.jlo.prueba.pase.exceptions.drivers.DriverIsNotActiveException;
import com.jlo.prueba.pase.exceptions.drivers.DriverNotFoundException;
import com.jlo.prueba.pase.exceptions.orders.OrderIsNotCreatedException;
import com.jlo.prueba.pase.exceptions.orders.OrderNotFoundException;
import com.jlo.prueba.pase.mappers.OrderMapper;
import com.jlo.prueba.pase.repositories.asignations.OrderAsignationRepository;
import com.jlo.prueba.pase.repositories.drivers.DriverRepository;
import com.jlo.prueba.pase.repositories.orders.OrderRepository;
import com.jlo.prueba.pase.services.asignations.AssignationsService;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
public class AssignationsServiceImpl implements AssignationsService {

    private final String UPLOAD_DIR = System.getProperty("user.dir")
            + File.separator + "uploads"
            + File.separator + "assignations" + File.separator;

    private final OrderRepository orderRepository;
    private final DriverRepository driverRepository;
    private final OrderAsignationRepository orderAsignationRepository;
    private final OrderMapper orderMapper;

    public AssignationsServiceImpl(OrderRepository orderRepository, DriverRepository driverRepository, OrderAsignationRepository orderAsignationRepository, OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.driverRepository = driverRepository;
        this.orderAsignationRepository = orderAsignationRepository;
        this.orderMapper = orderMapper;
    }

    @Override
    public AssignationResponse assignOrderToDriver(String driverId, String orderId
            , MultipartFile pdfFile, MultipartFile imageFile) throws IOException {

        if (!isValidUUID(driverId) || !isValidUUID(orderId)) {
            throw new FormatIdException("ID inválido: " + (isValidUUID(driverId) ? driverId : orderId));
        }

        ensureUploadDirectoryExists();
        validateFileExtension(pdfFile, "pdf");
        validateFileExtension(imageFile, "jpg", "jpeg", "png");

        Driver driverResult = driverRepository.findById(UUID.fromString(driverId)).orElseThrow(()
                -> new DriverNotFoundException("Conductor no encontrado con ID: " + driverId));

        if (!driverResult.isActive()) {
            throw new DriverIsNotActiveException("El conductor con ID: " + driverId + " no está activo");
        }

        Order orderResult = orderRepository.findById(UUID.fromString(orderId)).orElseThrow(()
                -> new OrderNotFoundException("Orden no encontrada con ID: " + orderId));

        if (orderResult.getStatus() != OrderStatus.CREATED) {
            throw new OrderIsNotCreatedException(" La orden con ID: " + orderId
                    + " no está en estado 'CREATED'");
        }

        orderAsignationRepository.findByOrderId(orderResult.getId())
                .ifPresent(existingAsignation -> {
                    throw new OrderIsAlreadyAssignedException("La orden con ID: " + orderId
                            + " ya está asignada a un conductor.");
                });


        OrderAsignationId orderAsignationId = new OrderAsignationId();
        orderAsignationId.setDriverId(driverResult.getId());
        orderAsignationId.setOrderId(orderResult.getId());

        String pathPdf = saveFile(pdfFile, "pdf");
        String pathImage = saveFile(imageFile, "image");

        OrdeAsignation newOrderAsignation = new OrdeAsignation();
        newOrderAsignation.setId(orderAsignationId);
        newOrderAsignation.setDriver(driverResult);
        newOrderAsignation.setOrder(orderResult);
        newOrderAsignation.setFilePathPdf(pathPdf);
        newOrderAsignation.setFilePathImage(pathImage);
        orderAsignationRepository.save(newOrderAsignation);

        OrderRequest orderRequest = orderMapper.orderToOrderRequest(orderResult);
        OrderResponseAssignation orderResponseAssignation = new OrderResponseAssignation(orderRequest,pathPdf, pathImage);
        List<OrderResponseAssignation> orderResponseAssignations = List.of(orderResponseAssignation);
        OrderAssignationResponse orderAssignationResponse = new OrderAssignationResponse(
                driverResult.getName(), driverResult.isActive(), orderResponseAssignations);
        List<OrderAssignationResponse> orderAssignationResponses = List.of(orderAssignationResponse);

        return new AssignationResponse(orderAssignationResponses, "Orden asignada correctamente al conductor con ID: " + driverId, HttpStatus.CREATED.value());
    }

    private void validateFileExtension(MultipartFile file, String... allowedExtensions) {
        String fileName = file.getOriginalFilename();

        if (file.isEmpty() || fileName == null || fileName.isBlank()) {
            throw new FilesEmptyException();
        }

        String fileExtension = fileName.substring(fileName.lastIndexOf(".") + 1).toLowerCase();
        for (String ext : allowedExtensions) {
            if (ext.equalsIgnoreCase(fileExtension)) {
                return;
            }
        }

        throw new MultipartException("Extensión de archivo no permitida. Permitidas: " + String.join(", ", allowedExtensions));
    }

    private String saveFile(MultipartFile file, String prefix) throws IOException {
        try {
            String fileName = prefix + "_" + "file";
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.write(path, file.getBytes());
            return path.toString();
        } catch (IOException e) {
            throw new IOException("Error al guardar el archivo", e);
        }
    }

    private void ensureUploadDirectoryExists() {
        int maxAttempts = 3;
        int attempt = 0;

        while (attempt < maxAttempts) {
            try {
                Path uploadPath = Paths.get(UPLOAD_DIR);
                if (!Files.exists(uploadPath)) {
                    Files.createDirectories(uploadPath);
                }
                return;
            } catch (IOException e) {
                attempt++;
                if (attempt == maxAttempts) {
                    throw new DirectoryCreationException("Falló después de " + maxAttempts + " intentos", e);
                }
                try {
                    Thread.sleep(1000); // Espera 1 segundo antes de reintentar
                } catch (InterruptedException ie) {
                    Thread.currentThread().interrupt();
                    throw new DirectoryCreationException("Operación interrumpida", ie);
                }
            }
        }
    }

    private boolean isValidUUID(String uuidString) {
        String regex = "^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[1-5][0-9a-fA-F]{3}-[89abAB][0-9a-fA-F]{3}-[0-9a-fA-F]{12}$";
        return Pattern.matches(regex, uuidString);
    }
}
