package com.kyterescue.services;
import com.kyterescue.entities.*;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import java.util.List;

@Service
public class FavoritesService {
    private UserRepository usersDao;

    FavoritesService(UserRepository usersDao) {
        this.usersDao = usersDao;
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
}
