package com.kyterescue.services;

import com.kyterescue.entities.FosterPet;
import com.kyterescue.entities.FosterPetRepository;
import com.kyterescue.entities.Pet;
import com.kyterescue.entities.PetRepository;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardFosterDisplayService {
    private final FosterPetRepository fostersDao;
    private final PetRepository petsDao;
    private final AuthenticationService authenticationService;

    DashboardFosterDisplayService(FosterPetRepository fostersDao, PetRepository petsDao, AuthenticationService authenticationService) {
        this.fostersDao = fostersDao;
        this.petsDao = petsDao;
        this.authenticationService = authenticationService;
    }
    public Pet grabCurrentFoster(Model model) {
        Pet currentFoster = null;
        List<FosterPet> allFosters = fostersDao.findFosterPetsOfUser(authenticationService.grabAuthenticationUserDetails(model));
        for (FosterPet foster : allFosters) {
            if (LocalDate.now().isAfter(foster.getStart_date()) && LocalDate.now().isBefore(foster.getEnd_date())) {
                currentFoster = petsDao.getPetById(foster.getPet().getId());
            }
        }
            return currentFoster;
    }
    public List<Pet> grabFosterHistory(Model model) {
        List<Pet> fosterHistory = new ArrayList<>();
        List<FosterPet> allFosters = fostersDao.findFosterPetsOfUser(authenticationService.grabAuthenticationUserDetails(model));
        for (FosterPet foster : allFosters) {
            if (LocalDate.now().isBefore(foster.getStart_date()) || LocalDate.now().isAfter(foster.getEnd_date())) {
                fosterHistory.add(petsDao.getPetById(foster.getPet().getId()));
                System.out.println(fosterHistory);
            }
        }
        return fosterHistory;
    }
}