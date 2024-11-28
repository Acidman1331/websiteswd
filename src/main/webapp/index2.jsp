<!DOCTYPE html>
<html>
<head>
    <title>Obtener Resolucion</title>
</head>
<body>
    <h1>Subir Imagen para Obtener Resolucion</h1>
    <form action="processPixelDensity" method="post" enctype="multipart/form-data">
        <label for="image">Seleccionar Imagen:</label>
        <input type="file" name="image" id="image" accept="image/*" required>
        <button type="submit">Procesar Imagen</button>
    </form>
</body>
</html>
