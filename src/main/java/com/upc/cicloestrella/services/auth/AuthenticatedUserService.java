package com.upc.cicloestrella.services.auth;

import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class AuthenticatedUserService {
    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final List<String> ALLOWED_ROLES = List.of("ROLE_ADMIN", "ROLE_MODERATOR");

    public Student getAuthenticatedStudent() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();
        return studentRepository.findStudentByUser_Email(username)
                .orElseThrow(() -> new RuntimeException("Estudiante no encontrado con email " + username));
    }

    public User getAuthenticatedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String username = authentication.getName();

        return userRepository.findByEmailAndStateTrue(username)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado con email " + username));
    }
}
