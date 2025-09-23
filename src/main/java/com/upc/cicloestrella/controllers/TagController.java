package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.requests.TagRequestDTO;
import com.upc.cicloestrella.DTOs.responses.TagResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.TagServiceInterface;
import jakarta.validation.Valid;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/tags")
public class TagController {
    private final TagServiceInterface tagService;

    @Autowired
    public TagController(TagServiceInterface tagService) {
        this.tagService = tagService;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<TagResponseDTO>>> index(@RequestParam(required = false) String keyword) {
        List<TagResponseDTO> tags = tagService.index(keyword);

        if (tags.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<TagResponseDTO>>builder()
                            .message("No se encontraron tags")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<List<TagResponseDTO>>builder()
                        .data(tags)
                        .message("Tags obtenidos correctamente")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponseDTO>> show(@PathVariable Long id) {
        TagResponseDTO tag = tagService.show(id);

        if (tag == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<TagResponseDTO>builder()
                            .message("Tag no encontrado")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<TagResponseDTO>builder()
                        .data(tag)
                        .message("Tag obtenido correctamente")
                        .status(200)
                        .build());

    }

    @PostMapping
    public ResponseEntity<ApiResponse<TagResponseDTO>> save(@Valid @RequestBody TagRequestDTO tagRequestDTO) {
        TagResponseDTO createdTag = tagService.save(tagRequestDTO);
        if (createdTag == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<TagResponseDTO>builder()
                            .message("Error al crear el tag")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<TagResponseDTO>builder()
                        .data(createdTag)
                        .message("Tag creado correctamente")
                        .status(201)
                        .build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody TagRequestDTO tagRequestDTO) {
        TagResponseDTO updatedTag = tagService.update(id, tagRequestDTO);
        if (updatedTag == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<TagResponseDTO>builder()
                            .message("Tag no encontrado")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<TagResponseDTO>builder()
                        .data(updatedTag)
                        .message("Tag actualizado correctamente")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<TagResponseDTO>> delete(@PathVariable Long id) {
        TagResponseDTO tag = tagService.show(id);
        if (tag == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<TagResponseDTO>builder()
                            .message("Tag no encontrado")
                            .status(404)
                            .build());
        }
        tagService.delete(id);
        return ResponseEntity.status(200)
                .body(ApiResponse.<TagResponseDTO>builder()
                        .data(tag)
                        .message("Tag eliminado correctamente")
                        .status(200)
                        .build());
    }
}
