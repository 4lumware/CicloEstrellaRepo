package com.upc.cicloestrella.controllers.application;


import com.upc.cicloestrella.DTOs.responses.RequestContentResponseDTO;
import com.upc.cicloestrella.DTOs.shared.ApiResponse;
import com.upc.cicloestrella.interfaces.services.application.RequestAdminInterface;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class RequestAdminController {
    private final RequestAdminInterface requestAdminInterface;

    @PostMapping("/requests/{requestId}/accept-request")
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<RequestContentResponseDTO>> acceptRequest(@PathVariable Long requestId) throws Exception {
        RequestContentResponseDTO request = requestAdminInterface.acceptRequest(requestId);
        return ResponseEntity.status(200)
                .body(ApiResponse.<RequestContentResponseDTO>builder()
                        .data(request)
                        .message("Se ha aceptado la solicitud con id: " + requestId)
                        .status(200)
                        .build());
    }
    @DeleteMapping("/requests/{requestId}/reject-request")
    @PreAuthorize("hasAnyRole('ADMIN' , 'MODERATOR')")
    public ResponseEntity<ApiResponse<RequestContentResponseDTO>> rejectRequest(@PathVariable Long requestId) throws Exception {
        RequestContentResponseDTO request = requestAdminInterface.rejectRequest(requestId);
        return ResponseEntity.status(200)
                .body(ApiResponse.<RequestContentResponseDTO>builder()
                        .message("Se ha rechazado la solicitud con id: " + requestId)
                        .status(200)
                        .data(request)
                        .build());
    }
}
