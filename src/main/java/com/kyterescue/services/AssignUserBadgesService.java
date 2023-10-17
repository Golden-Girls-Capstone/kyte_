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
        String petType = currentFoster.getType().toLowerCase();
        System.out.println(petType);
        return switch (petType) {
            case "barnyard" -> badgesDao.findBadgeById(1);
            case "dog" -> badgesDao.findBadgeById(2);
            case "rabbit" -> badgesDao.findBadgeById(3);
            case "small-furry" -> badgesDao.findBadgeById(4);
            case "scales-fins-other" -> badgesDao.findBadgeById(5);
            case "bird" -> badgesDao.findBadgeById(6);
            case "cat" -> badgesDao.findBadgeById(7);
//            case "old" -> badgesDao.findBadgeById(8);
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
