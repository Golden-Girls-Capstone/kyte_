package com.kyterescue.services;

import com.kyterescue.entities.Pet;
import com.kyterescue.entities.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;

@Service
public class SavePetToDatabaseService {
    private PetRepository petsDao;
    GrabApiDataService grabData;

    SavePetToDatabaseService(PetRepository petsDao, GrabApiDataService grabData) {
        this.petsDao = petsDao;
        this.grabData = grabData;
    }

}
