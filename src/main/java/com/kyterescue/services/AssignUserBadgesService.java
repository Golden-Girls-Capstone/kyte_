package com.kyterescue.services;
import com.kyterescue.entities.*;
import org.springframework.stereotype.Service;

@Service
public class AssignUserBadgesService {
    private UserRepository usersDao;
    private BadgeRepository badgesDao;
    private FosterPetRepository fostersDao;

    AssignUserBadgesService(
            UserRepository usersDao,
            BadgeRepository badgesDao,
            FosterPetRepository fostersDao
    ){
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

    public void assignBadgeToUser(User user, Badge badge){
        Badge badgeCheck = badgesDao.findBadgeById(badge.getId());
        if(!user.getBadges().contains(badgeCheck)){
            user.getBadges().add(badgeCheck);
            usersDao.save(user);
        }
    }
}
