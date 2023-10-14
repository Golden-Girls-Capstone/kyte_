package com.kyterescue.services;

import com.kyterescue.entities.*;
import org.springframework.security.core.annotation.CurrentSecurityContext;
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

    private final BadgeRespository badgesDao;

    private final ReviewRepository reviewsDao;
    private final AuthenticationService authenticationService;

    DashboardFosterDisplayService(FosterPetRepository fostersDao, BadgeRespository badgesDao, PetRepository petsDao, ReviewRepository reviewsDao, UserRepository usersDao, AuthenticationService authenticationService) {
        this.fostersDao = fostersDao;
        this.reviewsDao = reviewsDao;
        this.badgesDao = badgesDao;
        this.usersDao = usersDao;
        this.petsDao = petsDao;
        this.authenticationService = authenticationService;
    }
    public Pet grabCurrentFosterAsPet(@CurrentSecurityContext(expression = "authentication?.name") String username) {
        List<FosterPet> allFosters = fostersDao.findFosterPetsOfUser(usersDao.findByUsername(username));
        for (FosterPet foster : allFosters) {
            if(foster.getStatus()) {
                return petsDao.findByApiId(foster.getPet().getApiId());
            }
        }
            return null;
    }
    public FosterPet grabCurrentFosterAsFosterPet(@CurrentSecurityContext(expression = "authorization?.name") String username) {
        List<FosterPet> allFosters = fostersDao.findFosterPetsOfUser(usersDao.findByUsername(username));
        for(FosterPet foster : allFosters) {
            if(foster.getStatus()) {
                return foster;
            }
        }
        return null;
    }
    public List<Pet> grabFosterHistory(@CurrentSecurityContext(expression = "authentication?.name") String username) {
        List<Pet> fosterHistory = new ArrayList<>();
        List<FosterPet> allFosters = fostersDao.findFosterPetsOfUser(usersDao.findByUsername(username));
        for (FosterPet foster : allFosters) {
            if(!foster.getStatus()) {
                fosterHistory.add(petsDao.findByApiId(foster.getPet().getApiId()));
            }
        }
        return fosterHistory;
    }

    public List<Review> grabReviewHistory(@CurrentSecurityContext(expression = "authentication?.name") String username){
        List<Review> reviewHistory = reviewsDao.findReviewsOfUser(usersDao.findByUsername(username));
        return reviewHistory;
    }

    public List<Badge> grabBadgeHistory(@CurrentSecurityContext(expression = "authentication?.name") String username){
        List<Badge> badgeHistory = usersDao.findByUsername(username).getBadges();
        return badgeHistory;
    }


}