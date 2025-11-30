package com.upc.cicloestrella.controllers.application;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@RestController
@RequestMapping("/images")
public class ImageController {

    private final Path imagesBase = Paths.get("src", "main", "resources", "static", "images").toAbsolutePath().normalize();

    @GetMapping("/profiles/{filename:.+}")
    public ResponseEntity<Resource> serveProfileImage(@PathVariable String filename) {
        try {
            Path file = imagesBase.resolve("profiles").resolve(filename).normalize();

            if (!file.toAbsolutePath().startsWith(imagesBase)) {
                return ResponseEntity.notFound().build();
            }

            if (!Files.exists(file) || Files.isDirectory(file)) {
                return ResponseEntity.notFound().build();
            }

            Resource resource = new UrlResource(file.toUri());
            String contentType = Files.probeContentType(file);
            if (contentType == null) contentType = MediaType.APPLICATION_OCTET_STREAM_VALUE;

            return ResponseEntity.ok()
                    .contentType(MediaType.parseMediaType(contentType))
                    .body(resource);

        } catch (MalformedURLException e) {
            return ResponseEntity.badRequest().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }
}

