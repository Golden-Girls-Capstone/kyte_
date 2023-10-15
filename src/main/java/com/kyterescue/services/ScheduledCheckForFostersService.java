package com.kyterescue.services;

import com.kyterescue.entities.FosterPet;
import com.kyterescue.entities.FosterPetRepository;
import com.kyterescue.entities.User;
import com.kyterescue.entities.UserRepository;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@Configuration
@EnableScheduling
public class ScheduledCheckForFostersService {
    private UserRepository usersDao;
    private FosterPetRepository fostersDao;

    ScheduledCheckForFostersService(UserRepository usersDao, FosterPetRepository fostersDao) {
        this.usersDao = usersDao;
        this.fostersDao = fostersDao;
    }
    @Scheduled(cron = "0 28 17 * * ?")
    public void scheduledCheckAndAssign() {
        for(User user : usersDao.findAll()) {
            List<FosterPet> fosterPets = fostersDao.findFosterPetsOfUser(user);
            for(FosterPet pet : fosterPets) {
                if(!pet.getStatus() && Objects.equals(pet.getStart_date(), LocalDate.now())) {
                    pet.setStatus(true);
                    fostersDao.save(pet);
                }
            }
            usersDao.save(user);
        }
    }
}

