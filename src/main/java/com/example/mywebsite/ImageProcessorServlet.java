package com.example.mywebsite;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.formats.tiff.TiffContents;


import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@WebServlet(name = "ImageProcessor", urlPatterns = "/processImage")
public class ImageProcessorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html;charset=UTF-8");

        // Obtén el archivo de la solicitud
        Part filePart = req.getPart("image");
        if (filePart == null || filePart.getSize() == 0) {
            resp.getWriter().write("<p>Error: No se ha seleccionado ninguna imagen.</p>");
            return;
        }

        // Crea un archivo temporal para procesar la imagen
        File tempFile = File.createTempFile("uploaded-image", ".tmp");
        try {
            // Copia el contenido del archivo subido al archivo temporal
            Files.copy(filePart.getInputStream(), tempFile.toPath(), java.nio.file.StandardCopyOption.REPLACE_EXISTING);


            // Procesa la imagen con commons-imaging
            String metadata = Imaging.getMetadata(tempFile).toString();

            // Responde con los metadatos de la imagen
            resp.getWriter().write("<p>Metadatos de la Imagen:</p><pre>" + metadata + "</pre>");
        } catch (Exception e) {
            resp.getWriter().write("<p>Error procesando la imagen: " + e.getMessage() + "</p>");
        } finally {
            // Elimina el archivo temporal
            tempFile.delete();
        }
    }
}
