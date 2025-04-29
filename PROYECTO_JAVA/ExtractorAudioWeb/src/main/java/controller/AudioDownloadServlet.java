package controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/audios/*")
public class AudioDownloadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String audioName = request.getPathInfo();
        
        if (audioName == null || audioName.equals("/")) {
            response.sendError(HttpServletResponse.SC_BAD_REQUEST, "Nombre de archivo no especificado");
            return;
        }

        audioName = audioName.substring(1); // quitar "/" inicial

        File audioFile = new File("C:\\Users\\clipt\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ExtractorAudioWeb\\audios", audioName); //CAMBIAR HA RUTA DE LA CARPETA TEMPORAL DEL PROYECTO DENTRO DE LAS ("")

        if (!audioFile.exists() || audioFile.isDirectory()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND, "Audio no encontrado");
            return;
        }

        response.setContentType("audio/mpeg");
        response.setHeader("Content-Disposition", "attachment; filename=\"" + audioName + "\"");

        try (FileInputStream in = new FileInputStream(audioFile);
             OutputStream out = response.getOutputStream()) {

            byte[] buffer = new byte[4096]; // Mejor usar buffers un poco más grandes
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
    }
}
