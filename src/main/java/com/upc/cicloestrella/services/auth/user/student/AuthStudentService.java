package com.upc.cicloestrella.services.auth.user.student;

import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.auth.AuthStudentServiceInterface;
import com.upc.cicloestrella.mappers.StudentMapper;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthStudentService implements AuthStudentServiceInterface {
    private final StudentRepository studentRepository;
    private final StudentMapper studentMapper;


    @Override
    public StudentResponseDTO me() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        Student student = studentRepository.findStudentByUser_StateTrueAndUser_Email(username)
                .orElseThrow(() -> new EntityIdNotFoundException("Estudiante no encontrado con email " + username));

        return studentMapper.toDTO(student);
    }
}
