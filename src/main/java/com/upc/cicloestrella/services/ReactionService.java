package com.upc.cicloestrella.services;


import com.upc.cicloestrella.DTOs.requests.ReactionRequestDTO;
import com.upc.cicloestrella.DTOs.responses.ReactionResponseDTO;
import com.upc.cicloestrella.entities.Reaction;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.ReactionServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.ReactionRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReactionService implements ReactionServiceInterface {

    private final ReactionRepository reactionRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public ReactionService(ReactionRepository reactionRepository, ModelMapper modelMapper) {

        this.reactionRepository = reactionRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public ReactionResponseDTO save(ReactionRequestDTO reactionRequestDTO) {

        Reaction reaction = modelMapper.map(reactionRequestDTO, Reaction.class);
        Reaction savedReaction = reactionRepository.save(reaction);
        return modelMapper.map(savedReaction, ReactionResponseDTO.class);
    }

    @Override
    public List<ReactionResponseDTO> index(String reactionName) {

        if (reactionName != null && !reactionName.isEmpty()) {
            return reactionRepository.findByReactionNameContainingIgnoreCase(reactionName)
                    .stream()
                    .map(reaction -> modelMapper.map(reaction, ReactionResponseDTO.class))
                    .collect(Collectors.toList());
        }

        return reactionRepository.findAll()
                .stream()
                .map(reaction -> modelMapper.map(reaction, ReactionResponseDTO.class))
                .collect(Collectors.toList());
    }

    @Override
    public ReactionResponseDTO show(Long id) {

        return reactionRepository.findById(id)
                .map(reaction -> modelMapper.map(reaction, ReactionResponseDTO.class))
                .orElseThrow(() -> new EntityIdNotFoundException("Reacción con id " + id + " no encontrada"));
    }

    @Override
    public ReactionResponseDTO update(Long id, ReactionRequestDTO reactionRequestDTO) {

        Reaction existingReaction = reactionRepository.findById(id)
                .orElseThrow(() -> new EntityIdNotFoundException("Reacción con id " + id + " no encontrada"));
        modelMapper.map(reactionRequestDTO, existingReaction);
        Reaction updatedReaction = reactionRepository.save(existingReaction);
        return modelMapper.map(updatedReaction, ReactionResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (!reactionRepository.existsById(id)) {
            throw new EntityIdNotFoundException("Reacción con id " + id + " no encontrada");
        }
        reactionRepository.deleteById(id);
    }
}
