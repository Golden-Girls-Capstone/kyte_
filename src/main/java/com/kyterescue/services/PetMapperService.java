package com.kyterescue.services;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.*;
import org.springframework.stereotype.Service;
import java.io.IOException;

@Service
public class PetMapperService {
    private PetRepository petsDao;
    private FosterPetRepository fostersDao;
    private GrabApiDataService grabData;

    PetMapperService(PetRepository petsDao, FosterPetRepository fostersDao, GrabApiDataService grabData) {
        this.petsDao = petsDao;
        this.grabData = grabData;
        this.fostersDao = fostersDao;
    }

    public Pet checkAndMapToPet(Pet pet) {
        if(petsDao.existsByApiId(pet.getApiId())) {
            return petsDao.findByApiId(pet.getApiId());
        } else {
            petsDao.save(pet);
            return pet;
        }
    }
}
