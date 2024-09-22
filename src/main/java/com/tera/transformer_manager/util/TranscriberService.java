package com.tera.transformer_manager.util;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

@Service
public class TranscriberService {

    @Async  // Método no debe ser estático para que Spring lo maneje
    public void transcribeFile(String folderName, String fileName) {
        try {
            // 1. Determinar la duración del audio
            String filePath = "C:\\Users\\TERA\\Desktop\\output\\" + folderName + "\\" + fileName;
            String commandToDeterminateTime = "ffprobe -i " + filePath + " -show_entries format=duration -v quiet -of csv=\"p=0\"";

            ProcessBuilder timeProcessBuilder = new ProcessBuilder("cmd.exe", "/c", commandToDeterminateTime);
            runProcess(timeProcessBuilder);

            // 2. Ejecutar la transcripción con buzz
            String command = "buzz add --task transcribe --language es --model-type whisper --model-size tiny --txt " + filePath;
            ProcessBuilder transcriptionProcessBuilder = new ProcessBuilder("cmd.exe", "/c", command);
            runProcess(transcriptionProcessBuilder);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Método auxiliar para ejecutar procesos
    private void runProcess(ProcessBuilder processBuilder) throws IOException, InterruptedException {
        processBuilder.redirectErrorStream(true);  // Redirige el error al flujo de salida
        Process process = processBuilder.start();

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);  // Aquí puedes agregar lógica para actualizar progreso, logs, etc.
            }
        }

        int exitCode = process.waitFor();
        System.out.println("Proceso terminó con el código: " + exitCode);
    }
}
