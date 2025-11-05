package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.requests.StudentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.entities.Career;
import com.upc.cicloestrella.entities.Role;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.exceptions.UniqueException;
import com.upc.cicloestrella.interfaces.services.application.StudentServiceInterface;
import com.upc.cicloestrella.mappers.StudentMapper;
import com.upc.cicloestrella.repositories.interfaces.application.CareerRepository;
import com.upc.cicloestrella.repositories.interfaces.application.RoleRepository;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import com.upc.cicloestrella.repositories.interfaces.application.auth.UserRepository;
import com.upc.cicloestrella.services.logic.ImageCreatorService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Collections;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class StudentService implements StudentServiceInterface {

    private final StudentRepository studentRepository;
    private final UserRepository userRepository;
    private final CareerRepository careerRepository;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepository roleRepository;
    private final ImageCreatorService imageCreatorService;


    @Override
    public List<StudentResponseDTO> index() {
        return studentRepository.findAll()
                .stream()
                .map(studentMapper::toDTO)
                .toList();
    }

    @Override
    public StudentResponseDTO show(Long id) {
        return studentRepository.findById(id)
                .map(studentMapper::toDTO)
                .orElseThrow(() -> new EntityIdNotFoundException("Estudiante con id " + id + " no encontrado"));
    }

    @Override
    public StudentResponseDTO update(Long id, StudentRequestDTO userRequestDTO)  {

        if(userRepository.existsByEmailAndIdNot(userRequestDTO.getEmail() , id)) {
            throw new UniqueException("El email " + userRequestDTO.getEmail() + " ya está en uso.");
        }
        return studentRepository.findById(id)
                .map(existingStudent -> {



                    User existingUser = existingStudent.getUser();
                    existingUser.setUsername(userRequestDTO.getUsername());
                    existingUser.setEmail(userRequestDTO.getEmail());
                    if (userRequestDTO.getPassword() != null && !userRequestDTO.getPassword().isEmpty()) {
                        existingUser.setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));
                    }

                    List<Long> rolesSingleton = Collections.singletonList(userRequestDTO.getRoleId());
                    List<Role> roles = roleRepository.findAllById(rolesSingleton);
                    if (roles.isEmpty() || roles.size() != rolesSingleton.size()) {
                        throw new EntityIdNotFoundException("El rol proporcionado no existe");
                    }
                    existingUser.setRoles(roles);


                    List<Career> careers = careerRepository.findAllById(userRequestDTO.getCareerIds());
                    if(careers.isEmpty() || careers.size() != userRequestDTO.getCareerIds().size()) {
                        throw new EntityIdNotFoundException("Algunas carreras proporcionadas no existen o no se proporcionó ninguna");
                    }
                    existingStudent.setCareers(careers);
                    existingStudent.setCurrentSemester(userRequestDTO.getCurrentSemester());

                    String newProfileUrl = userRequestDTO.getProfilePictureUrl();
                    if(!newProfileUrl.equals(existingUser.getProfilePictureUrl())){
                        String[] parts = newProfileUrl.split(",");
                        if(parts.length != 2) throw new IllegalArgumentException("Formato de imagen inválido");
                        try {
                            existingUser.setProfilePictureUrl(imageCreatorService.saveBase64Image(parts[0], parts[1], userRequestDTO.getUsername()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    } else {
                        existingUser.setProfilePictureUrl(newProfileUrl);
                    }

                    Student updatedStudent = studentRepository.save(existingStudent);
                    return studentMapper.toDTO(updatedStudent);
                })
                .orElseThrow(() -> new EntityIdNotFoundException("Estudiante con id " + id + " no encontrado"));
    }

    @Override
    public void delete(Long id) {
        Student student = studentRepository.findById(id)
                .orElseThrow(() -> new EntityIdNotFoundException("Estudiante con id " + id + " no encontrado"));

        if (!studentRepository.existsById(id)) {
            throw new EntityIdNotFoundException("Estudiante con id " + id + " no encontrado");
        }

        User user = student.getUser();
        user.setState(false);
        studentRepository.save(student);


    }
}
