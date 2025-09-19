package com.upc.cicloestrella.services;

import com.upc.cicloestrella.DTOs.StudentRequestDTO;
import com.upc.cicloestrella.DTOs.StudentResponseDTO;
import com.upc.cicloestrella.entities.Career;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.User;
import com.upc.cicloestrella.interfaces.services.StudentServiceInterface;
import com.upc.cicloestrella.mappers.StudentMapper;
import com.upc.cicloestrella.repositories.interfaces.CareerRepository;
import com.upc.cicloestrella.repositories.interfaces.StudentRepository;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class StudentService implements StudentServiceInterface {

    private final StudentRepository studentRepository;
    private final CareerRepository careerRepository;
    private final ModelMapper modelMapper ;
    private final StudentMapper studentMapper;

    @Autowired
    public StudentService(StudentRepository studentRepository,  CareerRepository careerRepository, ModelMapper modelMapper, StudentMapper studentMapper) {
        this.studentRepository = studentRepository;
        this.modelMapper = modelMapper;
        this.studentMapper = studentMapper;
        this.careerRepository = careerRepository;
    }

    @Override
    @Transactional
    public StudentResponseDTO save(StudentRequestDTO userRequestDTO) {

        User user  = modelMapper.map(userRequestDTO, User.class);

        List<Career> careers  = careerRepository.findAllById(userRequestDTO.getCareerIds());

        if(careers.isEmpty() || careers.size() != userRequestDTO.getCareerIds().size()) {
            throw new IllegalArgumentException("Algunas carreras proporcionadas no existen o no se proporcionó ninguna");
        }

        user.setCreationDate(LocalDate.now());
        user.setState(true);

        Student student = Student.builder()
                .currentSemester(userRequestDTO.getCurrentSemester())
                .build();



        student.setUser(user);

        student.setCareers(careers);
        student.setCurrentSemester(userRequestDTO.getCurrentSemester());

        user.setStudent(student);

        Student savedStudent = studentRepository.save(student);

        return studentMapper.toDTO(savedStudent);

    }

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
                .orElse(null);
    }

    @Override
    public StudentResponseDTO update(Long id, StudentRequestDTO userRequestDTO) {
        return studentRepository.findById(id)
                .map(existingStudent -> {

                    modelMapper.map(userRequestDTO, existingStudent.getUser());

                    List<Career> careers  = careerRepository.findAllById(userRequestDTO.getCareerIds());

                    if(careers.isEmpty() || careers.size() != userRequestDTO.getCareerIds().size()) {
                        throw new IllegalArgumentException("Algunas carreras proporcionadas no existen o no se proporcionó ninguna");
                    }
                    existingStudent.setCareers(careers);

                    existingStudent.setCurrentSemester(userRequestDTO.getCurrentSemester());

                    Student updatedStudent = studentRepository.save(existingStudent);

                    return studentMapper.toDTO(updatedStudent);
                })
                .orElse(null);
    }

    @Override
    public void delete(Long id) {
        studentRepository.deleteById(id);
    }
}
