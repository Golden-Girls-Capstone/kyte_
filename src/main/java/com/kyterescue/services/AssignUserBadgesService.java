package com.kyterescue.services;
import com.kyterescue.entities.*;
import jakarta.persistence.Id;
import org.springframework.stereotype.Service;

import java.time.LocalDate;


@Service
public class AssignUserBadgesService {
    private UserRepository usersDao;
    private BadgeRespository badgesDao;

    private FosterPetRepository fostersDao;

    AssignUserBadgesService(UserRepository usersDao, BadgeRespository badgesDao, FosterPetRepository fostersDao){
        this.usersDao = usersDao;
        this.badgesDao = badgesDao;
        this.fostersDao = fostersDao;
    }

    public Badge getBadgeForUser(Pet currentFoster){
        String petType = currentFoster.getType();
        return switch (petType) {
            case "cat" -> badgesDao.findBadgeById(1);
            case "dog" -> badgesDao.findBadgeById(2);
            case "small-furry" -> badgesDao.findBadgeById(3);
            case "bird" -> badgesDao.findBadgeById(4);
            case "rabbit" -> badgesDao.findBadgeById(5);
            case "barnyard" -> badgesDao.findBadgeById(6);
            case "scales-fins-other" -> badgesDao.findBadgeById(7);
            default -> null;
        };
    }

    public Badge assignBadgeToUser(User user, Badge badge){
        Badge badgeCheck = badgesDao.findBadgeById(badge.getId());
        if(!user.getBadges().contains(badgeCheck)){
            user.getBadges().add(badgeCheck);
            usersDao.save(user);
        }
        return badgeCheck;
    }





//    User user = usersDao.findByUsername(username);
//    Pet currentFoster = dashboardFosterDisplayService.grabCurrentFosterAsPet(user);
//    FosterPet unsetCurrentFoster = dashboardFosterDisplayService.grabCurrentFosterAsFosterPet(user);
//        review.setUser(user);
//        review.setPet(currentFoster);
//        reviewsDao.save(review);
//        unsetCurrentFoster.setStatus(false);
//        unsetCurrentFoster.setEnd_date(LocalDate.now());
//        fostersDao.save(unsetCurrentFoster);



    //target user logged in

    //target the submit review button

    //run through foster history list

    //check for new unsigned value types of cat, dog, small-furry, bird, rabbit, barnyard, scales-fins-other

    //check badgeHistory

    //if there are new fosters that dont have a badge given already to the user in their badge history given them the appropriate badge

    //else do nothing



}
