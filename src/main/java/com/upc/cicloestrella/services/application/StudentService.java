package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.requests.StudentRequestDTO;
import com.upc.cicloestrella.DTOs.responses.StudentResponseDTO;
import com.upc.cicloestrella.entities.Career;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.StudentServiceInterface;
import com.upc.cicloestrella.mappers.StudentMapper;
import com.upc.cicloestrella.repositories.interfaces.application.CareerRepository;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ =  @Autowired)
public class StudentService implements StudentServiceInterface {

    private final StudentRepository studentRepository;
    private final CareerRepository careerRepository;
    private final ModelMapper modelMapper ;
    private final StudentMapper studentMapper;
    private final PasswordEncoder passwordEncoder;


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
    public StudentResponseDTO update(Long id, StudentRequestDTO userRequestDTO) {
        return studentRepository.findById(id)
                .map(existingStudent -> {

                    modelMapper.map(userRequestDTO, existingStudent.getUser());

                    List<Career> careers  = careerRepository.findAllById(userRequestDTO.getCareerIds());

                    if(careers.isEmpty() || careers.size() != userRequestDTO.getCareerIds().size()) {
                        throw new EntityIdNotFoundException("Algunas carreras proporcionadas no existen o no se proporcionó ninguna");
                    }
                    existingStudent.setCareers(careers);
                    existingStudent.setCurrentSemester(userRequestDTO.getCurrentSemester());
                    existingStudent.getUser().setPassword(passwordEncoder.encode(userRequestDTO.getPassword()));

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
