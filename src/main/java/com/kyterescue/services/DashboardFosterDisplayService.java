package com.kyterescue.services;

import com.kyterescue.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardFosterDisplayService {
    private final FosterPetRepository fostersDao;

    private final UserRepository usersDao;
    private final PetRepository petsDao;
    private final AuthenticationService authenticationService;

    DashboardFosterDisplayService(FosterPetRepository fostersDao, PetRepository petsDao, UserRepository usersDao, AuthenticationService authenticationService) {
        this.fostersDao = fostersDao;
        this.usersDao = usersDao;
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
    public List<Pet> grabPetHistory(Model model) {
        List<Pet> petHistory = new ArrayList<>();
        List<FosterPet> allFosters = fostersDao.findFosterPetsOfUser(authenticationService.grabAuthenticationUserDetails(model));
        for (FosterPet foster : allFosters) {
            if (LocalDate.now().isBefore(foster.getStart_date()) || LocalDate.now().isAfter(foster.getEnd_date())) {
                petHistory.add(petsDao.getPetById(foster.getPet().getId()));
            }
        }
        return petHistory;
    }

    public List<FosterPet> grabFosterHistory(Model model){
        List<FosterPet> fosterHistory = fostersDao.findFosterPetsOfUser(authenticationService.grabAuthenticationUserDetails(model));
        return fosterHistory;
    }


}