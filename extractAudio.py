from flask import Flask, request, send_file
from moviepy.editor import VideoFileClip
import os

app = Flask(__name__)

@app.route('/extract_audio', methods=['POST'])
def extract_audio():
    video = request.files['video']
    audio_format = request.form.get('format', 'mp3')  # Por defecto mp3 si no mandan formato
    video_path = 'uploads/uploaded_video.mp4'

    # Aseguramos extensión correcta
    audio_filename = f"extracted_audio.{audio_format}"
    audio_path = os.path.join('uploads', audio_filename)

    # Guardar el archivo de video
    video.save(video_path)

    # Usando moviepy para extraer el audio
    video_clip = VideoFileClip(video_path)
    audio = video_clip.audio

    # Guardar el audio en el formato especificado
    audio.write_audiofile(audio_path, codec=get_codec(audio_format))

    # Cerrar correctamente los objetos
    audio.close()
    video_clip.close()

    # Eliminar el archivo de video original
    if os.path.exists(video_path):
        os.remove(video_path)
        print(f"[DEBUG] Archivo de video {video_path} eliminado.")

    # Devolver el archivo de audio generado
    return send_file(audio_path, as_attachment=True)

def get_codec(format_name):
    # Mapeo formatos a codecs de ffmpeg
    codecs = {
        'mp3': 'libmp3lame',
        'wav': 'pcm_s16le',
        'aac': 'aac',
        'ogg': 'libvorbis'
    }
    return codecs.get(format_name, 'libmp3lame')  # Default: mp3

@app.route('/clean_uploads', methods=['POST'])
def clean_uploads():
    upload_folder = 'uploads'
    if os.path.exists(upload_folder):
        for filename in os.listdir(upload_folder):
            file_path = os.path.join(upload_folder, filename)
            try:
                if os.path.isfile(file_path):
                    os.remove(file_path)
                    print(f"Archivo eliminado: {file_path}")
            except Exception as e:
                print(f"Error eliminando {file_path}: {e}")

    return "Uploads limpiados", 200

if __name__ == "__main__":
    app.run(debug=True, port=5000)
