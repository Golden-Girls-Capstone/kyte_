package com.kyterescue.services;

import com.kyterescue.entities.FosterPet;
import com.kyterescue.entities.FosterPetRepository;
import com.kyterescue.entities.User;
import com.kyterescue.entities.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class FosterPetValidationService {
    private UserRepository usersDao;
    private FosterPetRepository fostersDao;

    FosterPetValidationService(UserRepository usersDao, FosterPetRepository fostersDao) {
        this.usersDao = usersDao;
        this.fostersDao = fostersDao;
    }
    public boolean startDateBeforeEndDate(LocalDate startDate, LocalDate endDate) {
        return !endDate.isBefore(startDate) && !startDate.isAfter(endDate);
    }
    public boolean userHasCurrentFoster(User user) {
        List<FosterPet> fosterPets = fostersDao.findFosterPetsOfUser(user);
        for(FosterPet foster : fosterPets) {
            if(foster.getStatus()) {
                return true;
            }
        }
        return false;
    }
    public boolean dateIsCurrent(LocalDate startDate) {
        return !startDate.isBefore(LocalDate.now());
    }
    public boolean dateHasNoConflicts(User user, LocalDate startDate, LocalDate endDate) {
        List<FosterPet> fosters = fostersDao.findFosterPetsOfUser(user);
        for(FosterPet foster : fosters) {
            if((startDate.isBefore(foster.getEnd_date()) && startDate.isAfter(foster.getStart_date())) || (endDate.isAfter(foster.getStart_date())) && endDate.isBefore(foster.getEnd_date())) {
                return false;
            }
        }
        return true;
    }
    public LocalDate converter(String date) {
        return LocalDate.of(Integer.parseInt(date.substring(0, 4)), Integer.parseInt(date.substring(5, 7)), Integer.parseInt(date.substring(8, 10)));
    }
}
