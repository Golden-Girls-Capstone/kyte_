package com.kyterescue.services;

import com.kyterescue.entities.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckForUniqueEmailService {
    private UserRepository usersDao;

    CheckForUniqueEmailService(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    public boolean check(String email) {
        if(usersDao.findByEmail(email) != null) {
            return false;
        } else return true;
    }
}
