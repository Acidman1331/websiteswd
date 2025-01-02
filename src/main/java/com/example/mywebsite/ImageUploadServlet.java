package com.example.mywebsite;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.apache.commons.io.IOUtils;


import java.io.*;
import java.util.*;

@WebServlet("/upload-image")
@MultipartConfig(
        maxFileSize = 1024 * 1024 * 5,      // 5MB
        maxRequestSize = 1024 * 1024 * 10    // 10MB
)
public class ImageUploadServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws  IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();

        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Upload Image</title>");
        out.println("<style>");
        out.println(".upload-container { max-width: 600px; margin: 50px auto; padding: 20px; border: 1px solid #ccc; border-radius: 5px; }");
        out.println(".upload-form { display: flex; flex-direction: column; gap: 10px; }");
        out.println(".submit-button { padding: 10px; background-color: #007bff; color: white; border: none; border-radius: 5px; cursor: pointer; }");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class='upload-container'>");
        out.println("<h2>Upload Image for Analysis</h2>");
        out.println("<form action='upload-image' method='post' enctype='multipart/form-data' class='upload-form'>");
        out.println("<input type='file' name='file' accept='image/*' required>");
        out.println("<button type='submit' class='submit-button'>Analyze Image</button>");
        out.println("</form>");
        out.println("</div>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            Part filePart = request.getPart("file");
            byte[] imageBytes = filePart.getInputStream().readAllBytes();
            String base64Image = Base64.getEncoder().encodeToString(imageBytes);

            // Extract metadata
            ImageMetadata metadata = Imaging.getMetadata(filePart.getInputStream(), filePart.getSubmittedFileName());
            Map<String, String> metadataMap = new HashMap<>();
            PrintWriter out = response.getWriter();

            File file = new File("path\\to\\your\\file");
            try(OutputStream outputStream = new FileOutputStream(file)){
                IOUtils.copy(filePart.getInputStream(), outputStream);
            } catch (FileNotFoundException e) {
                // handle exception here
            } catch (IOException e) {
                // handle exception here
            }

            List<String> imageInfo = new ArrayList<>();

            List<File> files = new List<File>() {
                @Override
                public int size() {
                    return 0;
                }

                @Override
                public boolean isEmpty() {
                    return false;
                }

                @Override
                public boolean contains(Object o) {
                    return false;
                }

                @Override
                public Iterator<File> iterator() {
                    return null;
                }

                @Override
                public Object[] toArray() {
                    return new Object[0];
                }

                @Override
                public <T> T[] toArray(T[] a) {
                    return null;
                }

                @Override
                public boolean add(File file) {
                    return false;
                }

                @Override
                public boolean remove(Object o) {
                    return false;
                }

                @Override
                public boolean containsAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean addAll(Collection<? extends File> c) {
                    return false;
                }

                @Override
                public boolean addAll(int index, Collection<? extends File> c) {
                    return false;
                }

                @Override
                public boolean removeAll(Collection<?> c) {
                    return false;
                }

                @Override
                public boolean retainAll(Collection<?> c) {
                    return false;
                }

                @Override
                public void clear() {

                }

                @Override
                public File get(int index) {
                    return null;
                }

                @Override
                public File set(int index, File element) {
                    return null;
                }

                @Override
                public void add(int index, File element) {

                }

                @Override
                public File remove(int index) {
                    return null;
                }

                @Override
                public int indexOf(Object o) {
                    return 0;
                }

                @Override
                public int lastIndexOf(Object o) {
                    return 0;
                }

                @Override
                public ListIterator<File> listIterator() {
                    return null;
                }

                @Override
                public ListIterator<File> listIterator(int index) {
                    return null;
                }

                @Override
                public List<File> subList(int fromIndex, int toIndex) {
                    return List.of();
                }
            } ;

            files.add((file));
            //List<BufferedImage> processedImages = Imaging.getBufferedImage(files,2);

            if (metadata != null) {
                if (metadata instanceof JpegImageMetadata) {
                    JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                    TiffImageMetadata tiffImageMetadata = jpegMetadata.getExif();


                    if (tiffImageMetadata != null) {

                        // Obtener campos TIFF
                        for (TiffField field : tiffImageMetadata.getAllFields()) {
                            imageInfo.add(String.format("Field: %s, Type: %s, Value: %s",
                                    field.getTagName(),
                                    field.getFieldType().getName(),
                                    field.getValue()));
                        }
                    }
                    else {
                            imageInfo.add(String.format("Field: %s, Type: %s, Value: %s",
                                    "N/D",
                                    "N/D",
                                    "N/D"));
                    }

                    if (jpegMetadata != null) {
                        for (TiffField field : jpegMetadata.getExif().getAllFields()) {
                            metadataMap.put(field.getTagName(), field.getValue().toString());
                        }
                    }
                }
                //metadataMap.put("hard", "codeeee");
                //metadataMap.put("meta", metadata.getClass().toString());

                // Display results
                response.setContentType("text/html");

                out.println("<!DOCTYPE html>");
                out.println("<html>");
                out.println("<head>");
                out.println("<title>Image Analysis Results</title>");
                out.println("<style>");
                out.println(".container { max-width: 800px; margin: 0 auto; padding: 20px; }");
                out.println(".image-container { margin: 20px 0; }");
                out.println(".image-container img { max-width: 100%; height: auto; border: 1px solid #ddd; border-radius: 4px; padding: 5px; }");
                out.println(".metadata-table { width: 100%; border-collapse: collapse; margin-top: 20px; }");
                out.println(".metadata-table th, .metadata-table td { border: 1px solid #ddd; padding: 8px; text-align: left; }");
                out.println(".metadata-table th { background-color: #f4f4f4; }");
                out.println("</style>");
                out.println("</head>");
                out.println("<body>");

                out.println("<div class='container'>");
                out.println("<h1>Results</h1>");

                out.println("<div class='image-container'>");
                out.println("<h2>Uploaded Image:</h2>");
                out.println("<img src='data:" + filePart.getContentType() + ";base64," + base64Image + "' alt='Uploaded image'/>");
                out.println("</div>");


                out.println("<div class=\"result-container\">");
                out.println("<h2>\"AbstractFieldType\" properties</h2>");
                out.println("<div class=\"results\">");

                // Generar resultados
                for (String info : imageInfo) {
                    out.println("<div class=\"field-info\">" + info + "</div>");
                }

                out.println("</div>");
                out.println("</div>"); // Cierre de results

                out.println("<hr>");

                out.println("<h2>Image Metadata:</h2>");
                out.println("<table class='metadata-table'>");
                out.println("<thead><tr><th>Property</th><th>Value</th></tr></thead>");
                out.println("<tbody>");
                for (Map.Entry<String, String> entry : metadataMap.entrySet()) {
                    out.println("<tr><td>" + entry.getKey() + "</td><td>" + entry.getValue() + "</td></tr>");
                }
                out.println("</tbody></table>");

                out.println("<div style='margin-top: 20px'>");
                out.println("<a href='upload-image'>Upload Another Image</a>");
                out.println("</div>");

                out.println("</div>");
                out.println("</body>");
                out.println("</html>");

            } else {
                out.println("No se encontraron metadatos.");
            }

                // Read metadata from uploaded image
           // ImageMetadata metadata = Imaging.getMetadata(file.getInputStream(), file.getOriginalFilename());

            //Map<String, String> metadataMap = new HashMap<>();

//            if (metadata instanceof JpegImageMetadata) {
//                JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
//                TiffImageMetadata tiffImageMetadata = jpegMetadata.getExif();
//
//
//                if (tiffImageMetadata != null) {
//                    for (TiffField field : tiffImageMetadata.getAllFields()) {
//                        metadataMap.put(field.getTagName(), field.getValue().toString());
//                    }
//                }
//            }


        } catch (Exception e) {
            response.getWriter().println("Error processing image: " + e.getMessage());
        }
    }
}