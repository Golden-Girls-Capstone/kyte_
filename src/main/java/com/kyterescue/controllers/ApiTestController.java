package com.kyterescue.controllers;

import com.kyterescue.entities.Pet;
import com.kyterescue.entities.PetRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ApiTestController {
    PetRepository petsDao;

    ApiTestController(PetRepository petsDao) {
        this.petsDao = petsDao;
    }

    @GetMapping("/api/test")
    public List<Pet> getAllPets() {
        return petsDao.findAll();
    }
}
