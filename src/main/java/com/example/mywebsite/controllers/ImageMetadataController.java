package com.example.mywebsite.controllers;

import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.ImageMetadata;
import org.apache.commons.imaging.formats.jpeg.JpegImageMetadata;
import org.apache.commons.imaging.formats.tiff.TiffField;
import org.apache.commons.imaging.formats.tiff.TiffImageMetadata;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import com.example.mywebsite.service.ImageService;

import java.util.HashMap;
import java.util.Map;

@Controller
public class ImageMetadataController {

    @Autowired
    private ImageService imageService;

    @GetMapping("/upload-image")
    public String uploadPage() {
        return "upload";
    }

    @PostMapping("/analyze-image")
    public String analyzeImage(@RequestParam("file") MultipartFile file, Model model) {
        try {
            // Convert image to Base64 for display
            String base64Image = imageService.convertToBase64(file);
            model.addAttribute("imageData", base64Image);
            model.addAttribute("contentType", file.getContentType());

            // Read metadata from uploaded image
            ImageMetadata metadata = Imaging.getMetadata(file.getInputStream(), file.getOriginalFilename());

            Map<String, String> metadataMap = new HashMap<>();

            if (metadata instanceof JpegImageMetadata) {
                JpegImageMetadata jpegMetadata = (JpegImageMetadata) metadata;
                TiffImageMetadata tiffImageMetadata = jpegMetadata.getExif();

                if (tiffImageMetadata != null) {
                    for (TiffField field : tiffImageMetadata.getAllFields()) {
                        metadataMap.put(field.getTagName(), field.getValue().toString());
                    }
                }
            }
            //metadataMap.put("hard", "code");

            model.addAttribute("metadata", metadataMap);
            return "metadata-result";
        } catch (Exception e) {
            model.addAttribute("error", "Error processing image: " + e.getMessage());
            return "error";
        }
    }
}