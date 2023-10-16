package com.kyterescue.services;

import com.kyterescue.entities.*;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.List;
import java.util.Objects;

@Service
public class AddFavoritesService {
    private UserRepository usersDao;
    private PetRepository petsDao;

    AddFavoritesService(UserRepository usersDao, PetRepository petsDao) {
        this.usersDao = usersDao;
        this.petsDao = petsDao;
    }

    public Pet add(Pet pet, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        User user = usersDao.findByUsername(username);
        List<Pet> currentFavorites = user.getFavorites();
            if(currentFavorites.contains(pet)) {
                user.getFavorites().remove(pet);
                usersDao.save(user);
                model.addAttribute("invalidFavorite", true);
            } else {
                currentFavorites.add(pet);
                user.setFavorites(currentFavorites);
                usersDao.save(user);
            }
            return pet;
    }
}
