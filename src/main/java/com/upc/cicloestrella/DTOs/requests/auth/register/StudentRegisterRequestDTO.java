package com.upc.cicloestrella.DTOs.requests.auth.register;

import jakarta.validation.constraints.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class StudentRegisterRequestDTO extends UserRegisterRequestDTO {

}
