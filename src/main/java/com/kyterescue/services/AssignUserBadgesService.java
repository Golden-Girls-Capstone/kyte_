package com.kyterescue.services;
import com.kyterescue.entities.*;
import org.springframework.stereotype.Service;


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

    public Badge assignBadge(User user, Pet currentFoster){
        String petType = currentFoster.getType();
        if(petType.equals("cat")){
            return badgesDao.findBadgeById(1);
        }
        if(petType.equals("dog")){
            return badgesDao.findBadgeById(2);
        }
        if(petType.equals("small-furry")){
            return badgesDao.findBadgeById(3);
        }
        if(petType.equals("bird")){
            return badgesDao.findBadgeById(4);
        }
        if(petType.equals("rabbit")){
            return badgesDao.findBadgeById(5);
        }
        if(petType.equals("barnyard")){
            return badgesDao.findBadgeById(6);
        }
        if(petType.equals("scales-fins-other")){
            return badgesDao.findBadgeById(7);
        }


    }

    //target user logged in

    //target the submit review button

    //run through foster history list

    //check for new unsigned value types of cat, dog, small-furry, bird, rabbit, barnyard, scales-fins-other

    //check badgeHistory

    //if there are new fosters that dont have a badge given already to the user in their badge history given them the appropriate badge

    //else do nothing



}
