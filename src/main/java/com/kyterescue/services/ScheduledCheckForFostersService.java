package com.kyterescue.services;

import com.kyterescue.entities.FosterPet;
import com.kyterescue.entities.FosterPetRepository;
import com.kyterescue.entities.UserRepository;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ScheduledCheckForFostersService {
    private UserRepository usersDao;
    private FosterPetRepository fostersDao;

    ScheduledCheckForFostersService(UserRepository usersDao, FosterPetRepository fostersDao) {
        this.usersDao = usersDao;
        this.fostersDao = fostersDao;
    }
    @Scheduled
    public void scheduledCheckAndAssign() {

    }
}
