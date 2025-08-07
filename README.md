# PruebaPase
Prueba pase

# API de Gestión de Órdenes y Conductores

Este proyecto es una API Spring Boot que gestiona asignaciones de órdenes a conductores, con soporte para subida de archivos (PDF e imágenes). Incluye los siguientes controladores:

- **`AssignationController`**: Asigna órdenes a conductores con archivos adjuntos.
- **`DriverController`**: Gestiona conductores (creación y consulta).
- **`OrderController`**: Maneja órdenes (creación, filtrado y actualización de estado).

## Requisitos
- Docker y Docker Compose instalados.

## Ejecución con Docker
- docker-compose down -v && docker-compose up --build