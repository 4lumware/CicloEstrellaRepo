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

import java.util.List;

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

        log.debug("StudentRegisterService.register: incoming DTO class={} dto={}", userRequestDTO != null ? userRequestDTO.getClass().getName() : null, userRequestDTO);

        if (userRequestDTO == null) {
            throw new IllegalArgumentException("El request de registro no puede ser null");
        }

        log.debug("StudentRegisterService.register: careerIds={}", userRequestDTO.getCareerIds());

        if (userRequestDTO.getCareerIds() == null || userRequestDTO.getCareerIds().isEmpty()) {
            throw new EntityIdNotFoundException("No se proporcionaron IDs de carreras válidos");
        }

        User user  = studentMapper.toUserEntity(userRequestDTO);

        user.setStudent(null);

        user = userRepository.save(user);

        roleService.assignRoleToUser(user  , Role.RoleName.STUDENT);
        user = userRepository.save(user);

        List<Career> careers  = careerRepository.findAllById(userRequestDTO.getCareerIds());

        if(careers.isEmpty() || careers.size() != userRequestDTO.getCareerIds().size()) {
            throw new EntityIdNotFoundException("Algunas carreras proporcionadas no existen o no se proporcionó ninguna");
        }

        Student student = Student.builder()
                .user(user)
                .careers(careers)
                .currentSemester(userRequestDTO.getCurrentSemester())
                .build();

        user.setStudent(student);

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
