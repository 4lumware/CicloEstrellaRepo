package com.upc.cicloestrella.services.auth.register;

import com.upc.cicloestrella.DTOs.requests.auth.register.StudentRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JWTTokensDTO;
import com.upc.cicloestrella.DTOs.responses.auth.JsonResponseDTO;
import com.upc.cicloestrella.entities.Career;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.exceptions.UniqueException;
import com.upc.cicloestrella.interfaces.services.auth.AuthRegisterInterface;
import com.upc.cicloestrella.mappers.StudentMapper;
import com.upc.cicloestrella.repositories.interfaces.application.CareerRepository;
import com.upc.cicloestrella.repositories.interfaces.application.RoleRepository;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.repositories.interfaces.auth.AuthServiceInterface;
import com.upc.cicloestrella.services.auth.AuthService;
import com.upc.cicloestrella.services.auth.JWTService;
import com.upc.cicloestrella.services.logic.ImageCreatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class AuthStudentRegister implements AuthRegisterInterface<JsonResponseDTO<StudentResponseDTO>, StudentRegisterRequestDTO> {

    private final UserRepository userRepository;
    private final StudentMapper studentMapper;
    private final RoleRepository roleRepository;
    private final CareerRepository careerRepository;
    private final StudentRepository studentRepository;
    private final AuthService authService;
    private final PasswordEncoder passwordEncoder;
    private final JWTService jwtService;
    private final ImageCreatorService imageCreatorService;


    @Transactional
    @Override
    public JsonResponseDTO<StudentResponseDTO> register(StudentRegisterRequestDTO dto) {

        if(userRepository.existsByEmail(dto.getEmail())) {
            throw new UniqueException("El email " + dto.getEmail() + " ya está en uso.");
        }

        User user = studentMapper.toUserEntity(dto);

        user.setStudent(null);
        user.setId(null);

        Role role = roleRepository.findByRoleName(Role.RoleName.STUDENT)
                .orElseThrow(() -> new EntityIdNotFoundException("Rol STUDENT no encontrado"));

        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setRoles(List.of(role));

        String profileImagePath = null;

        try {
            profileImagePath = imageCreatorService.generateDefaultProfileImage(dto.getProfilePictureUrl(), user.getUsername());
            user.setProfilePictureUrl(profileImagePath);
        } catch (IOException e) {
            throw new RuntimeException("Error al guardar la imagen de perfil");
        }

        User savedUser = userRepository.save(user);

        List<Career> careers = careerRepository.findAllById(dto.getCareerIds());

        if (careers.size() != dto.getCareerIds().size()) {
            throw new EntityIdNotFoundException("Algunas carreras proporcionadas no existen");
        }

        Student student = Student.builder()
                .user(savedUser)
                .careers(careers)
                .currentSemester(dto.getCurrentSemester())
                .build();

        savedUser.setStudent(student);

        Student savedStudent = studentRepository.save(student);
        String accessToken = jwtService.generateAccessToken(savedUser);
        String refreshToken = jwtService.generateRefreshToken(savedUser);

        authService.saveUserToken(savedUser, accessToken);

        StudentResponseDTO studentResponseDTO = studentMapper.toDTO(savedStudent);
        JWTTokensDTO tokensDTO = JWTTokensDTO.builder()
                .access_token(accessToken)
                .refresh_token(refreshToken)
                .build();

        return JsonResponseDTO.<StudentResponseDTO>builder()
                .user(studentResponseDTO)
                .tokens(tokensDTO)
                .build();

    }


}
