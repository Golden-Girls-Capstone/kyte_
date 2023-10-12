package com.kyterescue.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kyterescue.entities.*;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PetMapperService {
    private PetRepository petsDao;
    private FosterPetRepository fostersDao;
    private GrabApiDataService grabData;
    private ObjectMapper mapper = new ObjectMapper();

    PetMapperService(PetRepository petsDao, FosterPetRepository fostersDao, GrabApiDataService grabData) {
        this.petsDao = petsDao;
        this.grabData = grabData;
        this.fostersDao = fostersDao;
    }

    public Pet checkAndMapToPet(String id) throws IOException {
        if(petsDao.existsByApiId(id)) {
            return petsDao.findByApiId(id);
        } else {
            return mapper.readValue(grabData.findAnimalById(Long.parseLong(id)), Pet.class);
        }
    }

    public FosterPet mapPetToFosterPet(FosterPet foster, User user, Pet pet) {
        if (fostersDao.existsById(foster.getId())) {
            return fostersDao.findFosterPetById(foster.getId());
        } else {
        foster.setPet(pet);
        foster.setUser(user);
        return foster;
        }

    }
}
