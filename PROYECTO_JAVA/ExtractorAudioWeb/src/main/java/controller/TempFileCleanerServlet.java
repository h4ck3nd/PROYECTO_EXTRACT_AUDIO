package controller;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/cleanTempFiles")
public class TempFileCleanerServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException {
        System.out.println("[DEBUG] Inicio de limpieza de archivos temporales...");

        // Carpetas a limpiar
        String uploadFolderPath = "C:\\Users\\clipt\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ExtractorAudioWeb\\uploads";
        String audioFolderPath = "C:\\Users\\clipt\\eclipse-workspace\\.metadata\\.plugins\\org.eclipse.wst.server.core\\tmp0\\wtpwebapps\\ExtractorAudioWeb\\audios";

        // Limpiar uploads de Java
        limpiarCarpeta(uploadFolderPath, "[DEBUG] Archivo de subida (Java)");

        // Limpiar audios de Java
        limpiarCarpeta(audioFolderPath, "[DEBUG] Archivo de audio (Java)");

        // Llamar a Flask para limpiar también uploads
        try {
            URL url = new URL("http://localhost:5000/clean_uploads");
            HttpURLConnection flaskConnection = (HttpURLConnection) url.openConnection();
            flaskConnection.setRequestMethod("POST");
            flaskConnection.setDoOutput(true);

            try (OutputStream os = flaskConnection.getOutputStream()) {
                os.write("clean".getBytes()); // Opcional, Flask no necesita datos realmente
            }

            int responseCode = flaskConnection.getResponseCode();
            System.out.println("[DEBUG] Respuesta de Flask al limpiar uploads: " + responseCode);

            flaskConnection.disconnect();
        } catch (Exception e) {
            System.out.println("[ERROR] No se pudo conectar a Flask para limpiar uploads: " + e.getMessage());
        }

        response.setStatus(HttpServletResponse.SC_OK);
        System.out.println("[DEBUG] Limpieza de archivos completada.");
    }

    // Método auxiliar para limpiar cualquier carpeta
    private void limpiarCarpeta(String folderPath, String debugPrefix) {
        File folder = new File(folderPath);
        if (folder.exists() && folder.isDirectory()) {
            File[] files = folder.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isFile()) {
                        boolean deleted = file.delete();
                        System.out.println(debugPrefix + " " + file.getName() + " eliminado: " + deleted);
                    }
                }
            }
        } else {
            System.out.println(debugPrefix + " Carpeta no encontrada o no es un directorio: " + folderPath);
        }
    }
}
