# Servidor Flask para extracción de audio

Este pequeño servidor en `Python 3` expone un endpoint que permite extraer el audio de un vídeo enviado desde un cliente (por ejemplo, una aplicación `Java Servlet`) y devolver el archivo de audio en el formato deseado.

## Requisitos

- Python 3.x instalado

- Librerías Python necesarias:

```bash
pip install Flask moviepy
```

Crear manualmente una carpeta llamada uploads/ en el mismo directorio donde esté el script de Python.

## Estructura esperada

/tu-carpeta-del-proyecto
│
├── app.py            # <- Este script (o el nombre que uses)
├── uploads/          # <- Carpeta creada manualmente (necesaria)

> Nota: El servidor necesita la carpeta uploads/ para guardar archivos de video temporales y generar audios.

## Endpoints disponibles

### 1. POST /extract_audio

- **Descripción:** Recibe un archivo de video y extrae su audio en el formato especificado.

- **Parámetros:**

 - **video:** el archivo de vídeo (tipo multipart/form-data).

 - **format:** (opcional) formato de audio de salida (mp3, wav, aac, ogg). Por defecto mp3.

- **Respuesta:** Devuelve el archivo de audio generado como descarga directa.

## Ejemplo de uso en Java:

(Se envía el vídeo junto con el formato deseado y se recibe el audio procesado).

### 2. POST /clean_uploads

- **Descripción:** Limpia (elimina) todos los archivos de la carpeta uploads/.

- **Uso:** Útil para mantener limpia la carpeta de archivos temporales después de procesar.

## Código principal

El script realiza los siguientes pasos:

1. Guarda el vídeo recibido en uploads/uploaded_video.mp4.

2. Usa moviepy para extraer el audio del vídeo.

3. Guarda el audio en el formato deseado dentro de la carpeta uploads/.

4. Elimina el vídeo original una vez extraído el audio.

5. Devuelve el archivo de audio generado.

## Cómo ejecutarlo

En el directorio donde tienes el script:

```bash
python app.py
```

Por defecto, el servidor correrá en:

```
http://localhost:5000
```

## Notas adicionales

**No recomendado para producción tal cual:** si vas a usarlo en un entorno real, sería conveniente añadir gestión de errores más robusta, control de tamaños de subida, etc.

**moviepy** internamente usa ffmpeg, asegúrate de tenerlo instalado en tu sistema si ves errores relacionados con codecs.
