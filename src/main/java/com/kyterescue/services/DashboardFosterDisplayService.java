package com.kyterescue.services;
import com.kyterescue.entities.*;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;

@Service
public class DashboardFosterDisplayService {

    private FosterPetRepository fostersDao;
    private UserRepository usersDao;
    private PetRepository petsDao;


    DashboardFosterDisplayService(
            FosterPetRepository fostersDao,
            PetRepository petsDao,
            UserRepository usersDao
    ){
        this.fostersDao = fostersDao;
        this.usersDao = usersDao;
        this.petsDao = petsDao;
    }
    public Pet grabCurrentFosterAsPet(User user) {
        List<FosterPet> allFosters = fostersDao.findFosterPetsOfUser(usersDao.findByUsername(user.getUsername()));
        for (FosterPet foster : allFosters) {
            if(foster.getStatus()) {
                return petsDao.findByApiId(foster.getPet().getApiId());
            }
        }
            return null;
    }
    public FosterPet grabCurrentFosterAsFosterPet(User user) {
        List<FosterPet> allFosters = fostersDao.findFosterPetsOfUser(usersDao.findByUsername(user.getUsername()));
        for(FosterPet foster : allFosters) {
            if(foster.getStatus()) {
                return foster;
            }
        }
        return null;
    }
    public List<Pet> grabFosterHistory(User user) {
        List<Pet> fosterHistory = new ArrayList<>();
        List<FosterPet> allFosters = fostersDao.findFosterPetsOfUser(usersDao.findByUsername(user.getUsername()));
        for (FosterPet foster : allFosters) {
            if(!foster.getStatus()) {
                fosterHistory.add(petsDao.findByApiId(foster.getPet().getApiId()));
            }
        }
        return fosterHistory;
    }
}