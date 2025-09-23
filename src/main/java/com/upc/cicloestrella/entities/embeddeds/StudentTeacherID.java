package com.upc.cicloestrella.entities.embeddeds;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Embeddable
public class StudentTeacherID implements Serializable {
    private Long studentId;
    private Long teacherId;
}
