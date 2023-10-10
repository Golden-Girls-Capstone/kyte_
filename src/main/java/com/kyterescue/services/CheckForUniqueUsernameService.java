package com.kyterescue.services;

import com.kyterescue.entities.User;
import com.kyterescue.entities.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckForUniqueUsernameService {
    private UserRepository usersDao;

    CheckForUniqueUsernameService(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    public boolean check(String username) {
        if(usersDao.findByUsername(username) != null) {
            return false;
        } else return true;
    }
}
