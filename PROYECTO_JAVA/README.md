# Extractor de Audio - Módulo Java

Este proyecto permite subir un video a un servidor Java (Servlets), que luego se comunica con un backend Flask (Python) para extraer el audio en el formato elegido (mp3, wav, aac, ogg). Finalmente, el audio procesado se descarga desde el servidor Java.

----

## Estructura de los Servlets Java

### 1. `VideoUploadServlet` (`/uploadVideo`)

Responsable de:

- Recibir el video subido desde el formulario web.

- Guardarlo temporalmente en una carpeta local (`uploads/`).

- Hacer una solicitud HTTP a un servidor Flask (`http://localhost:5000/extract_audio`), enviando:

  - El archivo de video.

  - El formato de audio seleccionado (`mp3`, `wav`, `aac`, `ogg`).

- Recibir el audio extraído de Flask y guardarlo en la carpeta `audios/`.

- Responder al navegador con el nombre del audio generado en formato JSON.

Tecnologías usadas:

- `HttpServlet`

- `MultipartConfig` para subida de archivos.

- `Apache HttpClient` para comunicación HTTP con Flask.

Notas:

- Maneja errores si no se puede contactar a Flask o si ocurre un fallo de procesamiento.

- Guarda archivos temporalmente usando rutas absolutas locales.

### 2. `AudioDownloadServlet` (`/audios/*`)

Responsable de:

- Servir archivos de audio almacenados en la carpeta `audios/` como descargas HTTP.

- Se accede vía URLs como: `/audios/extracted_audio.mp3`.

Detalles técnicos:

- Verifica que el archivo solicitado exista.

- Configura los encabezados de respuesta para forzar la descarga (`Content-Disposition: attachment`).

- Usa streams para enviar los archivos de manera eficiente.

### 3. `TempFileCleanerServlet` (`/cleanTempFiles`)

Responsable de:

- Limpiar automáticamente los archivos temporales de las carpetas `uploads/` y `audios/`.

- También envía una solicitud `POST` al servidor Flask para que limpie su carpeta de `uploads`.

Comportamiento:

- Se activa después de que un usuario descarga el archivo de audio (después de 10 segundos).

- Elimina solo archivos, no carpetas.

- Registra en consola los archivos eliminados.

## Flujo resumido de la parte Java

Usuario sube un video desde la web.

`VideoUploadServlet` guarda el video y lo envía a Flask.

Flask devuelve el audio extraído.

`VideoUploadServlet` guarda el audio y responde al navegador con el nombre del archivo.

Usuario descarga el audio a través de AudioDownloadServlet.

`TempFileCleanerServlet` limpia archivos temporales para ahorrar espacio.

### Carpetas usadas

Carpeta	Propósito

`/uploads/`	Guardar videos subidos temporalmente
`/audios/`	Guardar los audios extraídos

> Nota: Las rutas de carpetas están configuradas de manera absoluta para Eclipse y servidores locales.

## Requisitos del lado Java

- Java EE (servlets).

- Apache HttpClient (`org.apache.httpcomponents`).

- Servidor local (como `Tomcat`).

- Flask server corriendo localmente en `http://localhost:5000`.

Notas adicionales

Este proyecto usa un sistema híbrido: Java maneja la web y la subida de archivos, mientras que Python (Flask) realiza el procesamiento pesado de audio.

Es importante tener el servidor Flask corriendo antes de hacer pruebas de extracción.

Se recomienda limpiar las carpetas periódicamente si no se activa la limpieza automática
