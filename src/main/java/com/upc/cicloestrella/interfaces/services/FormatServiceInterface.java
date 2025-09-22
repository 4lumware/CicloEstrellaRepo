package com.upc.cicloestrella.interfaces.services;


import com.upc.cicloestrella.DTOs.requests.FormatRequestDTO;
import com.upc.cicloestrella.DTOs.responses.FormatResponseDTO;

import java.util.List;

public interface FormatServiceInterface {
    public List<FormatResponseDTO> index();
    public FormatResponseDTO show(Long id);
    public FormatResponseDTO save(FormatRequestDTO format);
    public FormatResponseDTO update(Long id, FormatRequestDTO format);
    public void delete(Long id);

}
