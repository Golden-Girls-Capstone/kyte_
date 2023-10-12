package com.kyterescue.services;

import com.kyterescue.entities.FosterPet;
import com.kyterescue.entities.FosterPetRepository;
import com.kyterescue.entities.User;
import com.kyterescue.entities.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class AddFavoritesService {
    private UserRepository usersDao;
    private FosterPetRepository fostersDao;

    AddFavoritesService(UserRepository usersDao, FosterPetRepository fostersDao) {
        this.usersDao = usersDao;
        this.fostersDao = fostersDao;
    }

    public void add(User user, FosterPet fosterPet) {

    }
}
