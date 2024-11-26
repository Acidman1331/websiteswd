<!DOCTYPE html>
<html>
<head>
    <title>Procesar Imagen</title>
</head>
<body>
    <h1>Subir Imagen para Procesar</h1>
    <form action="processImage" method="post" enctype="multipart/form-data">
        <label for="image">Subir Imagen:</label>
        <input type="file" name="image" id="image" accept="image/*" required>
        <button type="submit">Procesar</button>
    </form>
</body>
</html>
