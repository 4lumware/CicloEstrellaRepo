package com.upc.cicloestrella.interfaces.services.application;

import com.upc.cicloestrella.DTOs.requests.TagRequestDTO;
import com.upc.cicloestrella.DTOs.responses.TagResponseDTO;

import java.util.List;

public interface TagServiceInterface {
    public TagResponseDTO save(TagRequestDTO tagRequestDTO);
    public List<TagResponseDTO> index(String keyword);
    public TagResponseDTO show(Long id);
    public TagResponseDTO update(Long id, TagRequestDTO tagRequestDTO);
    public void delete(Long id);
}
