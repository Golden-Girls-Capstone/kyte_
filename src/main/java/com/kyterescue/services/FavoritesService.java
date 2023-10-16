package com.kyterescue.services;

import com.kyterescue.entities.*;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;

@Service
public class FavoritesService {
    private UserRepository usersDao;
    private PetRepository petsDao;

    FavoritesService(UserRepository usersDao, PetRepository petsDao) {
        this.usersDao = usersDao;
        this.petsDao = petsDao;
    }

    public void toggleFavorite(Pet pet, User user, Model model) {
        List<Pet> currentFavorites = user.getFavorites();
            if(currentFavorites.contains(pet)) {
                user.getFavorites().remove(pet);
                usersDao.save(user);
                model.addAttribute("removeFavorite", true);
            } else {
                currentFavorites.add(pet);
                user.setFavorites(currentFavorites);
                usersDao.save(user);
                model.addAttribute("addFavorite", true);
            }
    }
    public void removeFavorite(Pet pet, User user, Model model) {
        user.getFavorites().remove(pet);
        usersDao.save(user);
        model.addAttribute("removeFavorite", true);
    }
}
