package com.upc.cicloestrella.services.auth;

import com.upc.cicloestrella.DTOs.requests.auth.register.StudentRegisterRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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

        User user  = studentMapper.toUserEntity(userRequestDTO);
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
        roleService.assignRoleToUser(user  , Role.RoleName.STUDENT);

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
