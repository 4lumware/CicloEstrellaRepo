package com.upc.cicloestrella.controllers;


import com.upc.cicloestrella.DTOs.FormalityDTO;
import com.upc.cicloestrella.entities.Formality;
import com.upc.cicloestrella.services.FormalityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("Formality")
public class FormalityController {
    @Autowired
    private FormalityService formalityService;

    @GetMapping("/{idFormality}")
    public Formality findById(@PathVariable Long idFormality) {
        return formalityService.findById(idFormality);
    }

    @GetMapping()
    public List<FormalityDTO> findAll() {
        return formalityService.findAll();
    }

    @PostMapping
    public FormalityDTO insert(@RequestBody FormalityDTO formalityDTO) {
        return formalityService.insert(formalityDTO);
    }

    @PutMapping
    public Formality update(@RequestBody Formality formality) {
        return formalityService.update(formality);
    }

    @DeleteMapping("/{idFormality}")
    public void delete(@PathVariable Long idFormality) {
        formalityService.delete(idFormality);
    }
}
