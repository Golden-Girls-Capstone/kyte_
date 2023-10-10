package com.kyterescue.entities;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ReviewRepository extends JpaRepository <Review, Long> {

    @Query("FROM FosterPet f WHERE f.id = ?1")
    FosterPet findFosterPetById(long id);
    @Query("FROM FosterPet f WHERE f.pet = ?1")
    FosterPet findFosterPetByPetId(Pet pet);
    @Query("FROM FosterPet f WHERE f.user = ?1")
    List<FosterPet> findFosterPetsOfUser(User loggedInUser);

}
