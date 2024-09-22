package com.tera.transformer_manager.controller;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;

@RestController
@RequestMapping("/transcriber")
public class FileDownloadController {

    @GetMapping("/download/{folderName}/{fileName}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String folderName, @PathVariable String fileName) {
        try {
            // Ruta completa al archivo que deseas descargar
            String filePath = "C:\\Users\\TERA\\Desktop\\output\\3f47808a-4c1c-4164-93d3-b9b639feb2de\\test (transcribed on 22-Sep-2024 10-27-44).txt";
            File file = new File(filePath);

            if (!file.exists()) {
                // Si el archivo no existe, devuelve un cuerpo vacío con el código 404
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }

            // Cargar el archivo como un recurso
            Resource resource = new FileSystemResource(file);

            // Configurar los encabezados HTTP para la descarga
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);

            // Devolver el archivo al cliente
            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (Exception e) {
            // Si ocurre algún otro error, devuelve una respuesta de error genérica
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }
}
