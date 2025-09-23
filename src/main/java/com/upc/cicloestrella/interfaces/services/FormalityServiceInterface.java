package com.upc.cicloestrella.interfaces.services;



import com.upc.cicloestrella.DTOs.FormalityDTO;
import com.upc.cicloestrella.entities.Formality;

import java.util.List;

public interface FormalityServiceInterface {
    public FormalityDTO findById(Long idFormality);
    public List<FormalityDTO> findAll(String keyword);
    public FormalityDTO insert(FormalityDTO formalityDTO);
    public FormalityDTO update(Formality formality);
    public void delete(Long idFormality);

}
