package com.upc.cicloestrella.DTOs.requests;


import com.upc.cicloestrella.enums.RequestTypeEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class RequestContentRequestDTO {

    @NotBlank(message = "El tipo de solicitud no puede estar vacío")
    private RequestTypeEnum requestType;

    @NotNull(message = "El contenido de la solicitud no puede estar vacío")
    private Object content;
}
