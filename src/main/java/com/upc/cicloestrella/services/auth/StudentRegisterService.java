package com.upc.cicloestrella.services.auth;

import com.upc.cicloestrella.DTOs.requests.auth.register.StudentRegisterRequestDTO;
import com.upc.cicloestrella.entities.Career;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.mappers.StudentMapper;
import com.upc.cicloestrella.repositories.interfaces.application.CareerRepository;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.repositories.interfaces.auth.RoleRegisterInterface;
import org.springframework.transaction.annotation.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class StudentRegisterService implements RoleRegisterInterface<StudentRegisterRequestDTO , Student> {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final RoleService roleService;
    private final CareerRepository careerRepository;
    private final StudentMapper studentMapper;

    @Transactional
    @Override
    public Student register(StudentRegisterRequestDTO userRequestDTO) {

        if (userRequestDTO == null) {
            throw new IllegalArgumentException("El request de registro no puede ser null");
        }

        if (userRequestDTO.getCareerIds() == null || userRequestDTO.getCareerIds().isEmpty()) {
            throw new EntityIdNotFoundException("No se proporcionaron IDs de carreras válidos");
        }

        if (userRepository.existsByEmail(userRequestDTO.getEmail())) {
            throw new IllegalArgumentException("El usuario con ese correo ya existe");
        }

        User user = studentMapper.toUserEntity(userRequestDTO);

        user.setStudent(null);
        user.setId(null); // casi muero aqui

        if (user.getCreationDate() == null) user.setCreationDate(LocalDate.now());
        if (user.getState() == null) user.setState(true);

        User savedUser = userRepository.save(user);


        roleService.assignRoleToUser(savedUser, Role.RoleName.STUDENT);
        savedUser = userRepository.save(savedUser);

        List<Career> careers = careerRepository.findAllById(userRequestDTO.getCareerIds());
        if (careers.size() != userRequestDTO.getCareerIds().size()) {
            throw new EntityIdNotFoundException("Algunas carreras proporcionadas no existen");
        }

        Student student = Student.builder()
                .user(savedUser)
                .careers(careers)
                .currentSemester(userRequestDTO.getCurrentSemester())
                .build();

        savedUser.setStudent(student);
        return studentRepository.save(student);
    }

    public Student getStudentByUserId(Long userId) {
        if (userId == null) {
            throw new IllegalArgumentException("El ID de usuario no puede ser null");
        }
        return studentRepository.findStudentByUserId(userId)
                .orElseThrow(() -> new EntityIdNotFoundException("Estudiante no encontrado con el ID de usuario: " + userId));
    }


}
