package com.upc.cicloestrella.services;

import com.upc.cicloestrella.DTOs.requests.TagRequestDTO;
import com.upc.cicloestrella.DTOs.responses.TagResponseDTO;
import com.upc.cicloestrella.entities.Tag;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import com.upc.cicloestrella.interfaces.services.TagServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.TagRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class    TagService  implements TagServiceInterface {

    private final ModelMapper modelMapper;
    private final TagRepository tagRepository;

    @Autowired
    public TagService(ModelMapper modelMapper, TagRepository tagRepository) {
        this.modelMapper = modelMapper;
        this.tagRepository = tagRepository;
    }

    @Override
    public TagResponseDTO save(TagRequestDTO tagRequestDTO) {
        Tag tag = modelMapper.map(tagRequestDTO, Tag.class);
        Tag savedTag = tagRepository.save(tag);
        return modelMapper.map(savedTag, TagResponseDTO.class);
    }

    @Override
    public List<TagResponseDTO> index(String keyword) {
        return keyword == null || keyword.isEmpty() ?
                tagRepository.findAll().stream()
                        .map(tag -> modelMapper.map(tag, TagResponseDTO.class))
                        .collect(Collectors.toList())
                :
                tagRepository.findByTagNameContainingIgnoreCase(keyword).stream()
                        .map(tag -> modelMapper.map(tag, TagResponseDTO.class))
                        .collect(Collectors.toList());
    }

    @Override
    public TagResponseDTO show(Long id) {
        return tagRepository
                .findById(id)
                .map(tag -> modelMapper.map(tag, TagResponseDTO.class))
                .orElseThrow(() -> new EntityIdNotFoundException("Tag con id " + id + " no encontrado"));
    }

    @Override
    public TagResponseDTO update(Long id, TagRequestDTO tagRequestDTO) {
        Tag existingTag = tagRepository.findById(id)
                .orElseThrow(() -> new EntityIdNotFoundException("Tag con id " + id + " no encontrado"));

        modelMapper.map(tagRequestDTO, existingTag);

        Tag updatedTag = tagRepository.save(existingTag);

        return modelMapper.map(updatedTag, TagResponseDTO.class);
    }

    @Override
    public void delete(Long id) {
        if (!tagRepository.existsById(id)) {
            throw new EntityIdNotFoundException("Tag con id " + id + " no encontrado");
        }
        tagRepository.deleteById(id);
    }
}
