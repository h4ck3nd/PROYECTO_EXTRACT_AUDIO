# 🎵 Extractor de Audio Profesional

Bienvenido al Extractor de Audio Profesional, una aplicación web que te permite subir un video, extraer su audio en varios formatos (mp3, wav, aac, ogg), y descargarlo directamente desde tu navegador.
Este proyecto combina Java Servlet para la gestión web y Python Flask para el procesamiento de audio.

## 📦 Tecnologías Utilizadas

- Java EE (Servlets + JSP)

- Python 3 (Flask + MoviePy)

- HTML5, CSS3, jQuery

- Servidor integrado en Eclipse

- Servidor Flask (Python) para endpoints de procesamiento

## 🚀 Instalación y Configuración

### 1. Clonar o descargar el proyecto

```bash
git clone https://github.com/tu_usuario/extractor-audio.git
```

### 2. Importar en Eclipse (para la parte Java)

Abre Eclipse IDE for Enterprise Java Developers.

Ve a `File` > `Import` > `Existing Projects into Workspace`.

Selecciona la carpeta del proyecto.

Asegúrate de que Eclipse reconozca el proyecto como Dynamic Web Project.

Requiere un servidor local configurado (Tomcat recomendado).

### 3. Configuración del entorno Python

**a) Instalación de dependencias**

Usando `PyCharm` o directamente en terminal (`PowerShell`, `CMD` o `Bash`):

```bash
pip install Flask moviepy
```

**b) Estructura esperada para Python**

Dentro del proyecto debe existir una carpeta `uploads/` donde `Flask` guardará temporalmente los videos.

Crea manualmente si no existe:

```bash
mkdir uploads
```

**c) Iniciar el servidor Flask**

#### Desde PyCharm:

Abre el archivo `Python` principal (`app.py` o donde tengas el código de Flask).

Click derecho sobre el archivo → `Run 'app'`.

#### Desde PowerShell o CMD:

Navega hasta el directorio del proyecto donde está el script de Flask:

```bash
python app.py
```

El servidor debería iniciar en:

```bash
http://localhost:5000
```

## ⚙️ ¿Cómo Funciona?

En la web, subes un video (`.mp4`).

Seleccionas el formato de audio de salida: `mp3`, `wav`, `aac` u `ogg`.

El backend Java envía el archivo al servidor `Python Flask`.

Flask extrae el audio usando `MoviePy` y devuelve un archivo listo para descargar.

Puedes descargar el audio generado directamente desde la misma página.

Al descargar, automáticamente se limpia el archivo temporal.

## 🧹 Limpieza Automática

Después de descargar un audio, el sistema limpia automáticamente los archivos temporales.

Tanto en `Java` (carpetas `/uploads` y `/audios`) como en `Python` (carpeta `/uploads`).

## 📸 Vista previa

(Agrega aquí si quieres una imagen de tu formulario cargada en tu repositorio.)

## 📋 Notas

Asegúrate de que `Flask` y `Eclipse` estén corriendo simultáneamente.

Flask debe correr antes de subir archivos desde la web, ya que el backend Java necesita comunicarse con él.

El servidor Java utiliza servlets personalizados (`uploadVideo`, `audios/*`, `cleanTempFiles`).

## 🤝 Créditos

Creado por [d1se0/h4ck3nd]

Con 💙 para proyectos que mezclan Java y Python de forma elegante.
