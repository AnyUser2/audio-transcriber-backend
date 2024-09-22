package com.tera.transformer_manager.controller;

import com.tera.transformer_manager.util.TranscriberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/transcriber")
public class AudioTranscriberController {

    @Autowired
    private TranscriberService transcriberService;

    @PostMapping("/upload")
    public ResponseEntity<String> uploadFile(@RequestParam("file") MultipartFile file) {
        if (file.isEmpty()) {
            return new ResponseEntity<>("Por favor, seleccione un archivo.", HttpStatus.BAD_REQUEST);
        }

        String folderName = UUID.randomUUID().toString();  // Nombre único para la carpeta

        try {
            // Ruta donde deseas guardar el archivo
            String folderPath = "C:\\Users\\TERA\\Desktop\\output\\" + folderName + "\\";
            File folder = new File(folderPath);

            // Asegúrate de que el directorio exista
            if (!folder.exists()) {
                folder.mkdirs();  // Crea el directorio si no existe
            }

            // Ruta completa del archivo que se va a guardar
            String filePath = folderPath + file.getOriginalFilename();
            File destinationFile = new File(filePath);

            // Guarda el archivo en la carpeta recién creada
            file.transferTo(destinationFile);

            // Aquí se inicia el proceso de transcripción en segundo plano
            transcriberService.transcribeFile(folderName, file.getOriginalFilename());

            // Devuelve una respuesta inmediatamente al cliente
            return new ResponseEntity<>("Archivo recibido y el servidor está trabajando en él.", HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Error al guardar el archivo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
