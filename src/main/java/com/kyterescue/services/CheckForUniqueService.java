package com.kyterescue.services;
import com.kyterescue.entities.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class CheckForUniqueService {
    private UserRepository usersDao;

    CheckForUniqueService(UserRepository usersDao) {
        this.usersDao = usersDao;
    }

    public boolean checkUsername(String username) {
        if(usersDao.findByUsername(username) != null) {
            return false;
        } else return true;
    }
    public boolean checkEmail(String email) {
        if(usersDao.findByEmail(email) != null) {
            return false;
        } else return true;
    }
}
