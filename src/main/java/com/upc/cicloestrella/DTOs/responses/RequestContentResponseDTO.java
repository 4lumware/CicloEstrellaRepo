package com.upc.cicloestrella.DTOs.responses;

import com.upc.cicloestrella.entities.Request;
import com.upc.cicloestrella.enums.RequestTypeEnum;
import lombok.*;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RequestContentResponseDTO {
    private Long id;
    private RequestTypeEnum requestType;
    private Object content;
    private Request.RequestStatus status;
    private LocalDateTime createdAt;
}
