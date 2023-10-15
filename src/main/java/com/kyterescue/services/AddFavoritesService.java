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

    public void add(Pet pet, @CurrentSecurityContext(expression = "authentication?.name") String username, Model model) {
        System.out.println("inside service");
        User user = usersDao.findByUsername(username);
        List<Pet> currentFavorites = user.getFavorites();
            if(currentFavorites.contains(pet)) {
                System.out.println("inside if true");
                user.getFavorites().remove(pet);
                usersDao.save(user);
                model.addAttribute("invalidFavorite", true);
            } else {
                System.out.println("inside if false");
                currentFavorites.add(pet);
                user.setFavorites(currentFavorites);
                usersDao.save(user);
            }
    }
}
