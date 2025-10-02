package com.upc.cicloestrella.controllers.application;


import com.upc.cicloestrella.DTOs.requests.TeacherRequestDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherFindByIdResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherSearchByKeywordResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.TeacherServiceInterface;
import jakarta.annotation.security.PermitAll;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherServiceInterface teacherService;
    private final ModelMapper modelMapper;

    @Autowired
    public TeacherController(TeacherServiceInterface teacherService, ModelMapper modelMapper) {
        this.teacherService = teacherService;
        this.modelMapper = modelMapper;
    }

    @GetMapping
    @PermitAll
    public ResponseEntity<ApiResponse<List<TeacherSearchByKeywordResponseDTO>>> index(@RequestParam(required = false) String name) {
        List<TeacherSearchByKeywordResponseDTO> teachers = teacherService.index(name);

        if (teachers.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<TeacherSearchByKeywordResponseDTO>>builder()
                            .message("No se han encontrado profesores")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<List<TeacherSearchByKeywordResponseDTO>>builder()
                        .data(teachers)
                        .message("Se han encontrado los profesores")
                        .status(200)
                        .build());
    }

    @GetMapping("/{id}")
    @PermitAll
    public ResponseEntity<ApiResponse<TeacherFindByIdResponseDTO>> show(@PathVariable Long id) {
        TeacherFindByIdResponseDTO teacher = teacherService.show(id);

        if (teacher == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<TeacherFindByIdResponseDTO>builder()
                            .message("Profesor no encontrado")
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<TeacherFindByIdResponseDTO>builder()
                        .data(teacher)
                        .message("Profesor obtenido correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> save(@Valid @RequestBody TeacherRequestDTO teacher) {
        TeacherResponseDTO savedTeacher = teacherService.save(teacher);

        if (savedTeacher == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<TeacherResponseDTO>builder()
                            .message("No se ha podido crear el profesor")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<TeacherResponseDTO>builder()
                        .data(savedTeacher)
                        .message("Se ha creado el profesor")
                        .status(201)
                        .build());
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<TeacherResponseDTO>> update(@PathVariable Long id, @Valid @RequestBody TeacherRequestDTO teacher) {
        TeacherResponseDTO updatedTeacher = teacherService.update(id, teacher);

        if (updatedTeacher == null) {
            return ResponseEntity.status(400)
                    .body(ApiResponse.<TeacherResponseDTO>builder()
                            .message("No se ha podido actualizar el profesor")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<TeacherResponseDTO>builder()
                        .data(updatedTeacher)
                        .message("Se ha actualizado el profesor")
                        .status(200)
                        .build());
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<TeacherFindByIdResponseDTO>> delete(@PathVariable Long id) {
        TeacherFindByIdResponseDTO teacher = teacherService.show(id);

        if (teacher == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<TeacherFindByIdResponseDTO>builder()
                            .message("Profesor no encontrado")
                            .status(404)
                            .build());
        }
        teacherService.delete(id);
        return ResponseEntity.status(200)
                .body(ApiResponse.<TeacherFindByIdResponseDTO>builder()
                        .data(teacher)
                        .message("Se ha eliminado el profesor")
                        .status(200)
                        .build());
    }


    @GetMapping("/search")
    @PermitAll
    public ResponseEntity<ApiResponse<List<TeacherResponseDTO>>> searchTeachers(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String campus,
            @RequestParam(required = false) String course,
            @RequestParam(required = false) String career) {

        // Validar que al menos uno tenga valor
        if ((name == null || name.trim().isEmpty()) &&
                (campus == null || campus.trim().isEmpty()) &&
                (course == null || course.trim().isEmpty()) &&
                (career == null || career.trim().isEmpty())) {

            return ResponseEntity.status(400)
                    .body(ApiResponse.<List<TeacherResponseDTO>>builder()
                            .message("Debe especificar al menos un parámetro de búsqueda: name, campus, course o career")
                            .status(400)
                            .build());
        }

        List<TeacherResponseDTO> result = teacherService.searchTeachers(name, campus, course, career)
                .stream()
                .map(t -> modelMapper.map(t, TeacherResponseDTO.class))
                .collect(Collectors.toList());

        if (result.isEmpty()) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<List<TeacherResponseDTO>>builder()
                            .message("No se encontraron profesores con los criterios especificados")
                            .status(404)
                            .build());
        }

        return ResponseEntity.ok(
                ApiResponse.<List<TeacherResponseDTO>>builder()
                        .data(result)
                        .message("Profesores encontrados")
                        .status(200)
                        .build()
        );
    }
}
