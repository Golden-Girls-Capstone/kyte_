package com.kyterescue.services;

import com.kyterescue.entities.*;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FullFosterPetValidationService {

    FosterPetValidationService fosterPetValidationService;
    DashboardFosterDisplayService dashboardFosterDisplayService;
    PetRepository petsDao;
    FosterPetRepository fostersDao;

    FullFosterPetValidationService(FosterPetValidationService fosterPetValidationService, DashboardFosterDisplayService dashboardFosterDisplayService, PetRepository petsDao, FosterPetRepository fostersDao) {
        this.fosterPetValidationService = fosterPetValidationService;
        this.petsDao = petsDao;
        this.fostersDao = fostersDao;
        this.dashboardFosterDisplayService = dashboardFosterDisplayService;
    }

    public FosterPet run(User user, Pet pet, String firstDate, String secondDate) {
        LocalDate startDate = fosterPetValidationService.converter(firstDate);
        LocalDate endDate = fosterPetValidationService.converter(secondDate);
        if(fosterPetValidationService.dateIsCurrent(startDate) && fosterPetValidationService.dateHasNoConflicts(user, startDate, endDate) && fosterPetValidationService.startDateBeforeEndDate(startDate, endDate) && !fosterPetValidationService.userHasCurrentFoster(user)) {
            FosterPet foster = new FosterPet(startDate, endDate, user, pet, true);
            fostersDao.save(foster);
            return foster;
        } else if(fosterPetValidationService.dateIsCurrent(startDate) && fosterPetValidationService.dateHasNoConflicts(user, startDate, endDate) && fosterPetValidationService.startDateBeforeEndDate(startDate, endDate) && fosterPetValidationService.userHasCurrentFoster(user)) {
            FosterPet foster = new FosterPet(startDate, endDate, user, pet, false);
            fostersDao.save(foster);
            return foster;
        } else {
            return dashboardFosterDisplayService.grabCurrentFosterAsFosterPet(user);
        }
    }
}
