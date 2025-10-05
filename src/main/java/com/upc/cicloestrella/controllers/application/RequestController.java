package com.upc.cicloestrella.controllers.application;


import com.upc.cicloestrella.DTOs.requests.RequestContentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.RequestServiceInterface;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestController {
    private final RequestServiceInterface requestService;


    @GetMapping("/students/{studentId}/requests")
    @PreAuthorize("hasRole('STUDENT') and @requestAuthorizationService.canAccess(authentication , #studentId)")
    public ResponseEntity<ApiResponse<RequestContentResponseDTO>> save(@PathVariable Long studentId ,  @Valid RequestContentRequestDTO requestContentRequestDTO) {
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
