package com.upc.cicloestrella.services.application;

import com.upc.cicloestrella.DTOs.requests.FormatRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FormatResponseDTO;
import com.upc.cicloestrella.interfaces.services.application.FormatServiceInterface;
import com.upc.cicloestrella.repositories.interfaces.application.FormatRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.upc.cicloestrella.entities.Format;
import com.upc.cicloestrella.exceptions.EntityIdNotFoundException;
import java.util.List;

@Service
public class FormatService implements FormatServiceInterface {

    private final FormatRepository formatRepository;
    private final ModelMapper modelMapper;

    @Autowired
    public FormatService(FormatRepository formatRepository, ModelMapper modelMapper) {
        this.formatRepository = formatRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<FormatResponseDTO> index() {
        return formatRepository.findAll()
                .stream()
                .map(format -> modelMapper.map(format, FormatResponseDTO.class))
                .toList();
    }

    @Override
    public FormatResponseDTO show(Long id) {
        return formatRepository.findById(id)
                .map(format -> modelMapper.map(format, FormatResponseDTO.class))
                .orElseThrow(() -> new EntityIdNotFoundException("Formato con id " + id + " no encontrado"));
    }

    @Override
    public FormatResponseDTO save(FormatRequestDTO format) {
        var formatEntity = modelMapper.map(format, Format.class);
        var savedFormat = formatRepository.save(formatEntity);
        return modelMapper.map(savedFormat, FormatResponseDTO.class);
    }

    @Override
    public FormatResponseDTO update(Long id, FormatRequestDTO format) {
        return formatRepository.findById(id)
                .map(existingFormat -> {
                    modelMapper.map(format, existingFormat);
                    var updatedFormat = formatRepository.save(existingFormat);
                    return modelMapper.map(updatedFormat, FormatResponseDTO.class);
                })
                .orElseThrow(() -> new EntityIdNotFoundException("Formato con id " + id + " no encontrado"));
    }

    @Override
    public void delete(Long id) {
        if (!formatRepository.existsById(id)) {
            throw new EntityIdNotFoundException("Formato con id " + id + " no encontrado");
        }
        formatRepository.deleteById(id);
    }
}
