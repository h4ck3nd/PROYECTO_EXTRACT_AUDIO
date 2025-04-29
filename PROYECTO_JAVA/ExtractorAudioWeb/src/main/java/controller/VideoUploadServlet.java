package controller;

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

import org.apache.http.client.methods.*;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.*;
import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.HttpResponse;

@WebServlet("/uploadVideo")
@MultipartConfig
public class VideoUploadServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("[DEBUG] Inicio de subida de video...");

        Part videoPart = request.getPart("videoFile");
        String videoFileName = videoPart.getSubmittedFileName();
        InputStream videoInputStream = videoPart.getInputStream();

        // Leer el formato seleccionado del formulario
        String audioFormat = request.getParameter("format");
        System.out.println("[DEBUG] Formato de audio seleccionado: " + audioFormat);

        // Obtener rutas dinámicas
        String uploadsDirPath = request.getServletContext().getRealPath("/uploads");
        String audiosDirPath = request.getServletContext().getRealPath("/audios");

        // Crear carpetas si no existen
        new File(uploadsDirPath).mkdirs();
        new File(audiosDirPath).mkdirs();

        // Guardar el archivo de video temporalmente
        File tempFile = new File(uploadsDirPath, videoFileName);
        try (FileOutputStream out = new FileOutputStream(tempFile)) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = videoInputStream.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
        }
        System.out.println("[DEBUG] Video guardado temporalmente en: " + tempFile.getAbsolutePath());

        // Enviar el archivo de video y el formato a Flask para procesarlo
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpPost httpPost = new HttpPost("http://localhost:5000/extract_audio");

            MultipartEntityBuilder builder = MultipartEntityBuilder.create();
            builder.addPart("video", new FileBody(tempFile, ContentType.APPLICATION_OCTET_STREAM, videoFileName));
            builder.addTextBody("format", audioFormat);

            httpPost.setEntity(builder.build());
            HttpResponse flaskResponse = client.execute(httpPost);
            System.out.println("[DEBUG] Petición enviada a Flask para extracción de audio.");

            // Obtener el archivo de audio de la respuesta de Flask
            InputStream audioStream = flaskResponse.getEntity().getContent();

            // Nombre dinámico del audio final
            String audioFileName = "extracted_audio." + audioFormat;

            // Guardar el audio
            File audioFile = new File(audiosDirPath, audioFileName);
            try (OutputStream out = new FileOutputStream(audioFile)) {
                byte[] buffer = new byte[1024];
                int bytesRead;
                while ((bytesRead = audioStream.read(buffer)) != -1) {
                    out.write(buffer, 0, bytesRead);
                }
            }
            System.out.println("[DEBUG] Audio guardado en: " + audioFile.getAbsolutePath());

            // Responder al navegador
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");
            PrintWriter writer = response.getWriter();

            writer.write("{\"status\":\"success\", \"audioName\":\"" + audioFileName + "\"}");
            writer.flush();
            System.out.println("[DEBUG] Respuesta enviada al cliente con audio: " + audioFileName);

        } catch (Exception e) {
            System.err.println("[ERROR] Error durante procesamiento: " + e.getMessage());
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error al procesar el archivo");
        }
    }
}
