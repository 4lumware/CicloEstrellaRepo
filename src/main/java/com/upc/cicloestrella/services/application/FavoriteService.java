package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.FormalityDTO;
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
import com.upc.cicloestrella.services.auth.AuthenticatedUserService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor(onConstructor_ = @Autowired)
public class FavoriteService implements FavoriteServiceInterface {

    private final AuthenticatedUserService authenticatedUserService;
    private final FavoriteRepository favoriteRepository;
    private final TeacherRepository teacherRepository;
    private final FormalityRepository formalityRepository;
    private final ModelMapper modelMapper;

    private Object getFavoriteData(Favorite favorite) {
        if (favorite.getFavoriteType() == Favorite.FavoriteType.TEACHER) {
            Teacher teacher = teacherRepository.findById(favorite.getReferenceId())
                    .orElseThrow(() -> new RuntimeException("Profesor no encontrado con id " + favorite.getReferenceId()));
            return modelMapper.map(teacher, TeacherResponseDTO.class);
        } else if (favorite.getFavoriteType() == Favorite.FavoriteType.FORMALITY) {
            Formality formality = formalityRepository.findById(favorite.getReferenceId())
                    .orElseThrow(() -> new RuntimeException("Formalidad no encontrada con id " + favorite.getReferenceId()));
            return modelMapper.map(formality, FormalityDTO.class);
        }

        return null;
    }

    @Override
    public FavoriteResponseDTO save(FavoriteRequestDTO favoriteRequestDTO) {

        Student student = authenticatedUserService.getAuthenticatedStudent();

        if (Favorite.FavoriteType.FORMALITY.equals(favoriteRequestDTO.getType())) {

            if (!formalityRepository.existsById(favoriteRequestDTO.getReferenceId())) {
                throw new RuntimeException("Formalidad no encontrada con id " + favoriteRequestDTO.getReferenceId());
            }

        } else if (Favorite.FavoriteType.TEACHER.equals(favoriteRequestDTO.getType())) {

            if (!teacherRepository.existsById(favoriteRequestDTO.getReferenceId())) {
                throw new RuntimeException("Profesor no encontrado con id " + favoriteRequestDTO.getReferenceId());
            }

        }

        boolean exists = favoriteRepository.existsByStudentAndFavoriteTypeAndReferenceId(
                student,
                favoriteRequestDTO.getType(),
                favoriteRequestDTO.getReferenceId()
        );

        if (exists) {
            throw new RuntimeException("El favorito ya existe para este estudiante");
        }

        Favorite favorite = modelMapper.map(favoriteRequestDTO, Favorite.class);
        favorite.setStudent(student);
        Favorite savedFavorite = favoriteRepository.save(favorite);
        Object data = getFavoriteData(savedFavorite);

        return FavoriteResponseDTO.builder()
                .id(savedFavorite.getId())
                .type(savedFavorite.getFavoriteType())
                .favorite(data)
                .build();
    }

    @Override
    public List<FavoriteResponseDTO> index() {
        Student student = authenticatedUserService.getAuthenticatedStudent();
        List<Favorite> favorites = favoriteRepository.findAllByStudent(student);

        return favorites.stream().map(favorite ->  {
            Object data = getFavoriteData(favorite);
            return FavoriteResponseDTO.builder()
                    .id(favorite.getId())
                    .type(favorite.getFavoriteType())
                    .favorite(data)
                    .build();
        }).toList();
    }

    @Override
    public FavoriteResponseDTO findById(Long favoriteId) {
        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new EntityIdNotFoundException("Favorito no encontrado con id " + favoriteId));
        Object data = getFavoriteData(favorite);
        return FavoriteResponseDTO.builder()
                .id(favorite.getId())
                .type(favorite.getFavoriteType())
                .favorite(data)
                .build();
    }

    @Override
    public FavoriteResponseDTO delete(Long favoriteId) {
        Student student = authenticatedUserService.getAuthenticatedStudent();

        Favorite favorite = favoriteRepository.findById(favoriteId)
                .orElseThrow(() -> new EntityIdNotFoundException("Favorito no encontrado con id " + favoriteId));

        if (!favorite.getStudent().getId().equals(student.getId())) {
            throw new AuthorizationDeniedException("No tienes permiso para eliminar este favorito");
        }

        Object data = getFavoriteData(favorite);
        favoriteRepository.delete(favorite);

        return FavoriteResponseDTO.builder()
                .id(favorite.getId())
                .type(favorite.getFavoriteType())
                .favorite(data)
                .build();
    }
}
