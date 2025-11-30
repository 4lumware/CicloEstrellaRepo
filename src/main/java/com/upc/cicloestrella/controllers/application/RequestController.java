package com.upc.cicloestrella.controllers.application;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.upc.cicloestrella.DTOs.requests.RequestContentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.entities.Request;
import com.upc.cicloestrella.enums.RequestTypeEnum;
import com.upc.cicloestrella.interfaces.services.application.RequestServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestController {
    private final RequestServiceInterface requestService;


    @GetMapping("/requests")
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<Page<RequestContentResponseDTO>>> index(
            @RequestParam(required = false) Request.RequestStatus status ,
            @RequestParam(required = false) RequestTypeEnum type ,
            @RequestParam(required = false) String studentName ,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate ,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate ,
            @RequestParam(required = false) String teacherName ,
            @RequestParam(required = false)  Long courseId ,
            @RequestParam(required = false) Long campusId ,
            @RequestParam(required = false)  Integer page ,
            @RequestParam(required = false)  Integer size
    ){

        int pageNumber = page != null ? page : 0;
        int pageSize = size != null ? size : 10;
        Page<RequestContentResponseDTO> requests = requestService.index(status , type , studentName , startDate , endDate , teacherName , courseId , campusId , pageNumber , pageSize);

        if(requests.isEmpty()){
            return ResponseEntity.status(404)
                    .body(ApiResponse.<Page<RequestContentResponseDTO>>builder()
                            .message("No hay solicitudes")
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<Page<RequestContentResponseDTO>>builder()
                        .data(requests)
                        .message("Se han encontrado las solicitudes")
                        .status(200)
                        .build());
    }

    @GetMapping("/requests/{requestId}")
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<RequestContentResponseDTO>> findById(@PathVariable Long requestId) throws JsonProcessingException {
        RequestContentResponseDTO request = requestService.findById(requestId);
        if (request == null) {
            return ResponseEntity.status(404)
                    .body(ApiResponse.<RequestContentResponseDTO>builder()
                            .message("No se encontro la solicitud con id: " + requestId)
                            .status(404)
                            .build());
        }
        return ResponseEntity.status(200)
                .body(ApiResponse.<RequestContentResponseDTO>builder()
                        .data(request)
                        .message("Se ha encontrado la solicitud con id: " + requestId)
                        .status(200)
                        .build());
    }

    @GetMapping("/students/{studentId}/requests")
    @PreAuthorize("hasAnyRole('STUDENT' , 'ADMIN' , 'MODERATOR') and @requestAuthorizationService.canAccess(authentication , #studentId)")
    public ResponseEntity<ApiResponse<Page<RequestContentResponseDTO>>> getByStudentId(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime startDate ,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime endDate ,
            @RequestParam(required = false) String teacherName ,
            @RequestParam(required = false)  Long courseId ,
            @RequestParam(required = false) Long campusId ,
            Pageable pageable,
            @PathVariable Long studentId
    ) {
        int page = pageable.getPageNumber();
        int size = pageable.getPageSize();
        Page<RequestContentResponseDTO> requests = requestService.allByStudentId(startDate , endDate , teacherName , courseId , campusId , studentId, page , size);

        if(requests.isEmpty()){
            return ResponseEntity.status(404)
                    .body(ApiResponse.<Page<RequestContentResponseDTO>>builder()
                            .message("No hay solicitudes para el estudiante con id: " + studentId)
                            .status(404)
                            .build());
        }

        return ResponseEntity.status(200)
                .body(ApiResponse.<Page<RequestContentResponseDTO>>builder()
                        .data(requests)
                        .message("Se han encontrado las solicitudes para el estudiante con id: " + studentId)
                        .status(200)
                        .build());
    }

    @DeleteMapping("/students/{studentId}/requests/{requestId}")
    @PreAuthorize("hasAnyRole('STUDENT' , 'MODERATOR' , 'ADMIN') and @requestAuthorizationService.canAccess(authentication , #studentId)")
    public ResponseEntity<ApiResponse<RequestContentResponseDTO>> delete(@PathVariable Long studentId , @PathVariable Long requestId) throws JsonProcessingException {
        RequestContentResponseDTO request = requestService.delete(studentId, requestId);

        return ResponseEntity.status(200)
                .body(ApiResponse.<RequestContentResponseDTO>builder()
                        .data(request)
                        .message("Solicitud eliminada correctamente")
                        .status(200)
                        .build());
    }

    @PostMapping("/students/{studentId}/requests")
    @PreAuthorize("hasRole('STUDENT') and @requestAuthorizationService.canAccess(authentication , #studentId)")
    public ResponseEntity<ApiResponse<RequestContentResponseDTO>> save(@PathVariable Long studentId ,  @Valid @RequestBody RequestContentRequestDTO requestContentRequestDTO) throws JsonProcessingException {
        RequestContentResponseDTO request = requestService.save(studentId, requestContentRequestDTO);
        if(request == null){
            return ResponseEntity.status(400)
                    .body(ApiResponse.<RequestContentResponseDTO>builder()
                            .message("Error al crear la solicitud")
                            .status(400)
                            .build());
        }
        return ResponseEntity.status(201)
                .body(ApiResponse.<RequestContentResponseDTO>builder()
                        .data(request)
                        .message("Solicitud creada correctamente")
                        .status(201)
                        .build());
    }


}
