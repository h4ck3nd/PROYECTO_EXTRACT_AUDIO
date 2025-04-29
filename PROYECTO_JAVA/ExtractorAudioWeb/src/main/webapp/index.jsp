<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Extractor de Audio Profesional</title>
    <link rel="stylesheet" href="<%= request.getContextPath() %>/css/style.css">
    <link rel="icon" type="image/x-icon" href="<%= request.getContextPath() %>/img/favicon.ico">
    <script src="https://code.jquery.com/jquery-3.6.0.min.js"></script>
    <style>
    @import url('https://fonts.googleapis.com/css2?family=Press+Start+2P&display=swap');
	
	body {
	    background-color: #0d1117;
	    color: #c9d1d9;
	    font-family: 'Press Start 2P', cursive;
	    padding: 40px;
	    text-align: center;
	}
	
	h1, h2, h3 {
	    color: #58a6ff;
	    margin-bottom: 20px;
	}
	
	form {
	    background-color: #161b22;
	    border-radius: 10px;
	    padding: 30px;
	    display: inline-block;
	    box-shadow: 0 0 15px rgba(88, 166, 255, 0.2);
	}
	
	label {
	    display: block;
	    margin: 15px 0 5px;
	    font-size: 14px;
	}
	
	input[type="file"],
	select {
	    width: 80%;
	    padding: 10px;
	    margin-bottom: 20px;
	    background-color: #0d1117;
	    border: 1px solid #30363d;
	    color: #c9d1d9;
	    font-size: 14px;
	}
	
	/* Botón de subida */
	input[type="submit"],
	button {
	    background-color: #238636;
	    color: white;
	    border: none;
	    padding: 12px 25px;
	    font-size: 14px;
	    cursor: pointer;
	    transition: background-color 0.3s, box-shadow 0.3s;
	    border-radius: 5px;
	    font-family: 'Press Start 2P', cursive;
	}
	
	input[type="submit"]:hover,
	button:hover {
	    background-color: #2ea043;
	    box-shadow: 0 0 10px #2ea043;
	}
	
	/* Contenedor de barra de progreso */
	.progress-container {
	    position: relative;
	    width: 80%;
	    margin: 30px auto;
	    height: 40px;
	}
	
	/* Barra de progreso */
	progress {
	    width: 100%;
	    height: 100%;
	    -webkit-appearance: none;
	    appearance: none;
	    border-radius: 10px;
	    overflow: hidden;
	    background-color: #30363d;
	    box-shadow: 0 0 10px #58a6ff80;
	    animation: glow 2s infinite;
	}
	
	progress::-webkit-progress-bar {
	    background-color: #30363d;
	    border-radius: 10px;
	}
	
	progress::-webkit-progress-value {
	    background: linear-gradient(90deg, #58a6ff, #1f6feb);
	    border-radius: 10px;
	    transition: width 0.4s ease;
	}
	
	progress::-moz-progress-bar {
	    background: linear-gradient(90deg, #58a6ff, #1f6feb);
	    border-radius: 10px;
	    transition: width 0.4s ease;
	}
	
	/* Texto del porcentaje */
	.progress-text {
	    position: absolute;
	    top: 0;
	    left: 50%;
	    transform: translate(-50%, 0);
	    color: #c9d1d9;
	    font-size: 14px;
	    font-family: 'Press Start 2P', cursive;
	    text-shadow: 0 0 5px #58a6ff;
	    line-height: 40px;
	    pointer-events: none;
	}
	
	/* Sección de descarga */
	#downloadSection a {
	    background-color: #238636;
	    color: white;
	    padding: 10px 20px;
	    text-decoration: none;
	    font-size: 14px;
	    border-radius: 5px;
	    display: inline-block;
	    margin-top: 20px;
	    transition: background-color 0.3s, box-shadow 0.3s;
	}
	
	#downloadSection a:hover {
	    background-color: #2ea043;
	    box-shadow: 0 0 10px #2ea043;
	}
	
	/* Animación de brillo */
	@keyframes glow {
	    0% {
	        box-shadow: 0 0 5px #58a6ff, 0 0 10px #58a6ff, 0 0 15px #58a6ff;
	    }
	    50% {
	        box-shadow: 0 0 10px #58a6ff, 0 0 20px #58a6ff, 0 0 30px #58a6ff;
	    }
	    100% {
	        box-shadow: 0 0 5px #58a6ff, 0 0 10px #58a6ff, 0 0 15px #58a6ff;
	    }
	}
    
    	.submit-btn {
		    background-color: #238636;
		    color: white;
		    border: none;
		    padding: 15px 30px;
		    font-size: 14px;
		    font-family: 'Press Start 2P', cursive;
		    cursor: pointer;
		    border-radius: 8px;
		    margin-top: 20px;
		    transition: background-color 0.3s, transform 0.2s, box-shadow 0.3s;
		    box-shadow: 0 0 8px #238636;
		}
		
		.submit-btn:hover {
		    background-color: #2ea043;
		    transform: scale(1.05);
		    box-shadow: 0 0 12px #2ea043, 0 0 24px #2ea043;
		}
		
		.submit-btn:active {
		    background-color: #196c2e;
		    transform: scale(0.98);
		    box-shadow: 0 0 5px #196c2e;
		}
    </style>
</head>
<body>
    <h1>Extraer Audio de Video</h1>
    <br><br><br><br>
    <div class="upload-container">
        <form id="uploadForm" action="<%= request.getContextPath() %>/uploadVideo" method="post" enctype="multipart/form-data">
        	<label for="video">Selecciona un video:</label><br>
		    <input type="file" name="videoFile" id="videoFile" required /><br><br>
		
		    <label for="format">Formato de audio:</label><br>
		    <select name="format" id="format" required>
		        <option value="mp3">MP3</option>
		        <option value="wav">WAV</option>
		        <option value="aac">AAC</option>
		        <option value="ogg">OGG</option>
		    </select><br><br>
		
		    <input type="submit" value="Extraer Audio" class="submit-btn"/>
		</form>
        <div class="progress-container">
		    <progress id="progressBar" value="0" max="100" style="display: none;"></progress>
		    <div class="progress-text" id="progressText" style="display: none;">0%</div>
		</div>
        <div id="downloadSection" style="display: none;">
		    <a id="downloadLink" href="#" download="">Descargar Audio</a>
		</div>
    </div>

    <script>
    // Actualización de la barra de progreso y manejo de la carga
    $('#uploadForm').submit(function(event) {
        event.preventDefault();
        var formData = new FormData(this);
        var progressBar = $('#progressBar');
        var progressText = $('#progressText'); // <<-- añadimos esto
        var downloadSection = $('#downloadSection');
        var downloadLink = $('#downloadLink');

        progressBar.show();
        progressText.show(); // <<-- mostramos también el texto

        $.ajax({
            url: '<%= request.getContextPath() %>/uploadVideo',
            type: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            xhr: function() {
                var xhr = new XMLHttpRequest();
                xhr.upload.addEventListener('progress', function(e) {
                    if (e.lengthComputable) {
                        var percent = Math.round((e.loaded / e.total) * 100);
                        progressBar.val(percent);
                        progressText.text(percent + "%"); // <<-- actualizar porcentaje visible
                    }
                }, false);
                return xhr;
            },
            success: function(data) {
                progressBar.hide();
                progressText.hide(); // <<-- ocultamos el texto al terminar

                // Mostrar la sección de descarga
                downloadSection.show();

                // Obtener el nombre real del audio generado desde la respuesta
                var audioName = data.audioName; // Por ejemplo: extracted_audio.wav

                // Construir la URL correcta para descargar
                var audioURL = '<%= request.getContextPath() %>/audios/' + audioName;

                // Actualizar dinámicamente el enlace
                downloadLink.attr('href', audioURL);
                downloadLink.attr('download', audioName);

                // Evitar que se registre más de un click listener si suben varios archivos
                downloadLink.off('click').on('click', function() {
                    // Después de hacer clic para descargar, esperar 10 segundos y limpiar
                    setTimeout(function() {
                        $.ajax({
                            url: '<%= request.getContextPath() %>/cleanTempFiles',
                            type: 'POST'
                        });
                    }, 10000); // 10 segundos
                });
            },
            error: function() {
                alert('Error en la carga del video');
                progressBar.hide();
                progressText.hide(); // <<-- también ocultamos en caso de error
            }
        });
    });
</script>
</body>
</html>
