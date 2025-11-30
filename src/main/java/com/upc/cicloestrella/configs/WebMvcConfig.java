package com.upc.cicloestrella.configs;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.file.Path;
import java.nio.file.Paths;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler("/images/**")
                .addResourceLocations("classpath:/static/images/")
                .addResourceLocations(pathToExternalImages());
    }

    private String pathToExternalImages() {
        Path imagesDir = Paths.get("src", "main", "resources", "static", "images").toAbsolutePath();
        return imagesDir.toUri().toString();
    }
}

