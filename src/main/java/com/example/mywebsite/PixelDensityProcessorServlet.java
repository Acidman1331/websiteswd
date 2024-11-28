package com.example.mywebsite;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.RationalNumber;

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

@WebServlet(name = "PixelDensityProcessor", urlPatterns = "/processPixelDensity")
@MultipartConfig
public class PixelDensityProcessorServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part filePart = req.getPart("image");

        if (filePart == null || filePart.getSize() == 0) {
            resp.getWriter().write("No se subió ninguna imagen.");
            return;
        }

        File tempFile = File.createTempFile("uploaded-image", ".tmp");
        //File file = new File(System.getProperty("java.io.tmpdir"), filename);

        try {
            Files.copy(filePart.getInputStream(), tempFile.toPath());
            int h = Imaging.getImageInfo(tempFile).getHeight();
            int w = Imaging.getImageInfo(tempFile).getWidth();

            if (h != 0 && w != 0) {
                resp.getWriter().write("<h2>Alto y ancho de la imagen:</h2>");
                resp.getWriter().write("<p>ALTO: " + h + " píxeles</p>");
                resp.getWriter().write("<p>ANCHO: " + w + " píxeles </p>");
            } else {
                resp.getWriter().write("La imagen no contiene información de densidad de píxeles.");
            }
        } catch (Exception e) {
            resp.getWriter().write("Error leyendo la imagen: " + e.getMessage());
        } finally {
           // tempFile.delete();
        }
    }
}
