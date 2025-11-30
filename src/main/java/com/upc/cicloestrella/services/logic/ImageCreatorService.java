package com.upc.cicloestrella.services.logic;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.*;
import java.util.Base64;
import java.util.UUID;

@Service
public class ImageCreatorService {
    @Value("${spring.api-url}")
    private String apiUrl;

    public String saveBase64Image(String base64Prefix,    String base64ImageData, String username) throws IOException {
        byte[] imageBytes = Base64.getDecoder().decode(base64ImageData);

        Path uploadDir = Paths.get("src/main/resources/static/images/profiles");
        Files.createDirectories(uploadDir);

        String extension = getString(base64Prefix);
        String fileName = null;
        boolean created = false;
        for (int attempt = 0; attempt < 5 && !created; attempt++) {
            String candidate = username + "_" + UUID.randomUUID() + "." + extension;
            Path candidatePath = uploadDir.resolve(candidate);
            try {
                Files.write(candidatePath, imageBytes, StandardOpenOption.CREATE_NEW);
                fileName = candidate;
                created = true;
            } catch (FileAlreadyExistsException fae) {
                // colisión improbable; probar otra UUID
            }
        }

        if (!created) {
            String candidate = username + "_" + System.currentTimeMillis() + "." + extension;
            Path candidatePath = uploadDir.resolve(candidate);
            Files.write(candidatePath, imageBytes, StandardOpenOption.CREATE);
            fileName = candidate;
        }

        return apiUrl + "/images/profiles/" + fileName;
    }

    public void deleteImage(String fileName) throws IOException {
        Path imagePath = Paths.get("src/main/resources/static/images/profiles/").resolve(fileName);
        Files.deleteIfExists(imagePath);
    }

    private static String getString(String prefix) {
        String extension = "png";
        try {
            if (prefix.contains("/")) {
                String mime = prefix.substring(prefix.indexOf('/') + 1);
                if (mime.contains(";")) {
                    mime = mime.substring(0, mime.indexOf(';'));
                }
                if (mime.equalsIgnoreCase("jpeg") || mime.equalsIgnoreCase("pjpeg")) extension = "jpg";
                else if (mime.equalsIgnoreCase("png")) extension = "png";
                else if (mime.equalsIgnoreCase("gif")) extension = "gif";
                else extension = mime;
            }
        } catch (Exception ignored) {
        }
        return extension;
    }

    public  String generateDefaultProfileImage(String base64 , String username) throws IOException {

        String prefix = null;
        String data = null;

        if (base64.contains(",")) {
            String[] parts = base64.split(",");
            prefix = parts[0];
            data = parts[1];
        } else {
            prefix = "data:image/png;base64";
            data = base64;
        }

        return saveBase64Image(prefix, data, username);
    }
}
