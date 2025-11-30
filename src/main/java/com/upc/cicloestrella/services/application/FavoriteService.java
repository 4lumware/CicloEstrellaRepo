package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.responses.FormalityResponseDTO;
import com.upc.cicloestrella.DTOs.requests.FavoriteRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FavoriteResponseDTO;
import com.upc.cicloestrella.DTOs.responses.teachers.TeacherResponseDTO;
import com.upc.cicloestrella.entities.Favorite;
import com.upc.cicloestrella.entities.Formality;
import com.upc.cicloestrella.entities.Student;
import com.upc.cicloestrella.entities.Teacher;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.application.FavoriteServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.FavoriteRepository;
import com.upc.cicloestrella.repositories.interfaces.application.FormalityRepository;
import com.upc.cicloestrella.repositories.interfaces.application.StudentRepository;
import com.upc.cicloestrella.repositories.interfaces.application.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FavoriteService implements FavoriteServiceInterface {

    private final StudentRepository studentRepository;
    private final FavoriteRepository favoriteRepository;
    private final TeacherRepository teacherRepository;
    private final FormalityRepository formalityRepository;
    private final ModelMapper modelMapper;

    private Object getFavoriteData(Favorite favorite) {
        if (favorite.getFavoriteType() == Favorite.FavoriteType.TEACHER) {
            Teacher teacher = teacherRepository.findById(favorite.getReferenceId())
                    .orElseThrow(() -> new EntityIdNotFoundException("Profesor no encontrado con id " + favorite.getReferenceId()));
            return modelMapper.map(teacher, TeacherResponseDTO.class);
        } else if (favorite.getFavoriteType() == Favorite.FavoriteType.FORMALITY) {
            Formality formality = formalityRepository.findById(favorite.getReferenceId())
                    .orElseThrow(() -> new EntityIdNotFoundException("Formalidad no encontrada con id " + favorite.getReferenceId()));
            return modelMapper.map(formality, FormalityResponseDTO.class);
        }

        return null;
    }

    @Override
    public FavoriteResponseDTO save(Long studentId, FavoriteRequestDTO favoriteRequestDTO) {

        Student student =  studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityIdNotFoundException("Estudiante no encontrado con id " + studentId));

        if (Favorite.FavoriteType.FORMALITY.equals(favoriteRequestDTO.getType())) {

            if (!formalityRepository.existsById(favoriteRequestDTO.getReferenceId())) {
                throw new EntityIdNotFoundException("Formalidad no encontrada con id " + favoriteRequestDTO.getReferenceId());
            }

        } else if (Favorite.FavoriteType.TEACHER.equals(favoriteRequestDTO.getType())) {

            if (!teacherRepository.existsById(favoriteRequestDTO.getReferenceId())) {
                throw new EntityIdNotFoundException("Profesor no encontrado con id " + favoriteRequestDTO.getReferenceId());
            }

        }

        boolean exists = favoriteRepository.existsByStudentAndFavoriteTypeAndReferenceId(
                student,
                favoriteRequestDTO.getType(),
                favoriteRequestDTO.getReferenceId()
        );

        if (exists) {
            throw new IllegalArgumentException("El favorito ya existe para este estudiante");
        }

        Favorite favorite = modelMapper.map(favoriteRequestDTO, Favorite.class);
        favorite.setId(null);
        favorite.setStudent(student);
        Favorite savedFavorite = favoriteRepository.save(favorite);
        Object data = getFavoriteData(savedFavorite);

        return FavoriteResponseDTO.builder()
                .id(savedFavorite.getId())
                .type(savedFavorite.getFavoriteType())
                .note(savedFavorite.getNote())
                .favorite(data)
                .build();
    }

    @Override
    public List<FavoriteResponseDTO> index(Long studentId) {
        List<Favorite> favorites = favoriteRepository.findAllByStudent_User_Id(studentId);

        return favorites.stream().map(favorite ->  {
            Object data = getFavoriteData(favorite);
            return FavoriteResponseDTO.builder()
                    .id(favorite.getId())
                    .type(favorite.getFavoriteType())
                    .note(favorite.getNote())
                    .favorite(data)
                    .build();
        }).toList();
    }

    @Override
    public FavoriteResponseDTO findById(Long studentId, Long favoriteId) {
        Favorite favorite = favoriteRepository
                .findByIdAndStudent_User_Id(favoriteId, studentId)
                .orElseThrow(() -> new EntityIdNotFoundException("No se encontró el favorito"));

        Object data = getFavoriteData(favorite);
        return FavoriteResponseDTO.builder()
                .id(favorite.getId())
                .type(favorite.getFavoriteType())
                .favorite(data)
                .note(favorite.getNote())
                .build();
    }

    @Transactional
    @Override
    public FavoriteResponseDTO delete(Long studentId, Long favoriteId) {
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityIdNotFoundException("Estudiante no encontrado con id " + studentId));

        Favorite favorite = favoriteRepository.findByIdAndStudent_User_Id(favoriteId, studentId)
                .orElseThrow(() -> new EntityIdNotFoundException("No se encontró el favorito para el estudiante especificado"));

        Object data = getFavoriteData(favorite);
        student.getFavorites().remove(favorite);

        return FavoriteResponseDTO.builder()
                .id(favorite.getId())
                .type(favorite.getFavoriteType())
                .favorite(data)
                .note(favorite.getNote())
                .build();
    }
}
