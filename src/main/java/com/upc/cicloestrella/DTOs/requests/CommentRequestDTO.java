package com.upc.cicloestrella.DTOs.requests;


import jakarta.persistence.Column;
import lombok.Data;


@Data
public class CommentRequestDTO {

    @Column(nullable = false, length = 2000)
    private String text;
}
