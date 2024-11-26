package com.example.mywebsite;

import org.apache.commons.imaging.Imaging;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;

@WebServlet(name = "ImageProcessor", urlPatterns = "/processImage")
public class ImageProcessorServlet2 extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Ruta de la imagen (simulada aquí, pero puedes obtenerla del formulario)
        File imageFile = new File("ruta/a/imagen.jpg");

        try {
            // Lee metadatos de la imagen
            String metadata = Imaging.getMetadata(imageFile).toString();
            resp.getWriter().write("Metadatos de la Imagen: " + metadata);
        } catch (Exception e) {
            resp.getWriter().write("Error procesando la imagen: " + e.getMessage());
        }
    }
}

