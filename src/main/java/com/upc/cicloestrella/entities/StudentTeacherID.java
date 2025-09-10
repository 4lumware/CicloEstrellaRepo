package com.upc.cicloestrella.entities;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Embeddable
public class StudentTeacherID implements Serializable {
    private Long studentId;
    private Long teacherId;
}
